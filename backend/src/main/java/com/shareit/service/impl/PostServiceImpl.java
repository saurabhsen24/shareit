package com.shareit.service.impl;

import com.shareit.dto.projection.PostProjection;
import com.shareit.dto.projection.VoteProjection;
import com.shareit.dto.request.PostRequestDto;
import com.shareit.dto.response.PostResponseDto;
import com.shareit.entities.Post;
import com.shareit.entities.User;
import com.shareit.enums.VoteType;
import com.shareit.exception.BadRequestException;
import com.shareit.exception.ForbiddenResourceException;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.repository.PostRepository;
import com.shareit.service.FileUploadService;
import com.shareit.service.PostService;
import com.shareit.service.UserService;
import com.shareit.utils.JwtHelper;
import com.shareit.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadService fileUploadService;


    @Override
    public void createPost(PostRequestDto postRequestDto) {

        log.debug("Creating post {}", postRequestDto.getPostTitle());

        String loggedInUsername = JwtHelper.getCurrentLoggedInUsername();

        User user = userService.getUserByUsername(loggedInUsername);

        String postUrl = getPostUrl( postRequestDto.getFile() );

        Post post = Post.builder()
                .postTitle(postRequestDto.getPostTitle())
                .postDescription(postRequestDto.getPostDescription())
                .user(user)
                .voteCount(0)
                .build();

        if(StringUtils.isNotBlank(postUrl)) {
            post.setPostUrl( postUrl );
        }

        log.debug("Post {} saved successfully", post.getPostTitle());
        postRepository.save(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {

        log.debug("Getting all the posts from db");

        List<PostProjection> posts = postRepository.findAllPosts();

        List<Long> postIds = posts.stream().map(post -> post.getPostId()).collect(Collectors.toList());

        List<VoteProjection> usersWhoLikedPost = postRepository.findAllUsersWhoLikedPost(postIds);

        Map<String,List<VoteProjection>> postLikedByUsers = usersWhoLikedPost.stream()
                .collect(Collectors.groupingBy(VoteProjection::getUserName));


        return posts.stream().map(post ->
                PostResponseDto.from(post, Utils.checkIfUserVotedThePost(postLikedByUsers, post.getPostId())))
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getAllPostsByUser(String userName) {

        log.debug("Getting all the posts from db for user {}", userName);

        List<PostProjection> posts = postRepository.findAllPostsByUser(userName);

        List<Long> postIds = posts.stream().map(post -> post.getPostId()).collect(Collectors.toList());

        List<VoteProjection> usersWhoLikedPost = postRepository.findAllUsersWhoLikedPost(postIds);

        Map<String,List<VoteProjection>> postLikedByUsers = usersWhoLikedPost.stream()
                .collect(Collectors.groupingBy(VoteProjection::getUserName));


        return posts.stream().map(post ->
                        PostResponseDto.from(post, Utils.checkIfUserVotedThePost(postLikedByUsers, post.getPostId())))
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto getPostById(Long postId) {
        PostProjection post = postRepository.findPost( postId );
        List<VoteProjection> usersWhoLikedPost = postRepository.findAllUsersWhoLikedPost(Collections.singletonList(postId));

        Map<String,List<VoteProjection>> postLikedByUsers = usersWhoLikedPost.stream()
                .collect(Collectors.groupingBy(VoteProjection::getUserName));

        return PostResponseDto.from(post, Utils.checkIfUserVotedThePost(postLikedByUsers, post.getPostId()));
    }

    @Override
    public void deletePost(Long postId) {

        if(BooleanUtils.isFalse(postRepository.existsById(postId))) {
            throw new ResourceNotFoundException("Post Not Found");
        }

        String postOwner = postRepository.findPostCreator(postId);
        String loggedInUser = JwtHelper.getCurrentLoggedInUsername();

        if(BooleanUtils.isFalse(postOwner.equals(loggedInUser))) {
            throw new ForbiddenResourceException("You can't delete this post");
        }

        postRepository.deleteById(postId);

    }

    @Override
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        log.info("Updating post {}", postId);

        Post updatedPost = findPostByPostId(postId);

        String postOwner = postRepository.findPostCreator(postId);
        String loggedInUser = JwtHelper.getCurrentLoggedInUsername();

        if(BooleanUtils.isFalse(postOwner.equals(loggedInUser))) {
            throw new ForbiddenResourceException("You can't update this post");
        }

        if (StringUtils.isNotBlank(postRequestDto.getPostTitle())) {
            updatedPost.setPostTitle(postRequestDto.getPostTitle());
        }

        if (StringUtils.isNotBlank(postRequestDto.getPostDescription())) {
            updatedPost.setPostDescription(postRequestDto.getPostDescription());
        }

        String postUrl = getPostUrl( postRequestDto.getFile() );

        if (StringUtils.isNotBlank(postUrl)) {
           updatedPost.setPostUrl(postUrl);
        }

        log.info("Post updated successfully {}", postId);
        postRepository.save(updatedPost);
    }

    public Post findPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found!"));
    }

    @Override
    public Integer updateVoteCount(Post post, VoteType voteType) {
        log.info("Updating the vote {} of Post {}", voteType.name(), post.getPostId());

        switch (voteType) {
            case UP_VOTE:
                post.setVoteCount(post.getVoteCount() + 1);
                break;
            case DOWN_VOTE:
                post.setVoteCount(post.getVoteCount() - 1);
                break;
            default:
                return post.getVoteCount();
        }

        post = postRepository.save(post);

        log.info("Post vote count updated {}", post.getVoteCount());

        return post.getVoteCount();
    }

    private String getPostUrl(MultipartFile file) {
        String postUrl = null;

        if(Objects.nonNull( file )) {
            try {
                postUrl = fileUploadService.uploadFile( file );
            } catch (IOException e) {
                log.warn("Some error occurred while uploading the file: {}", ExceptionUtils.getStackTrace(e));
                throw new BadRequestException("Something went wrong");
            }
        }

        return postUrl;
    }

}
