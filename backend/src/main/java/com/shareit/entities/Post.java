package com.shareit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "posts" )
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private UUID postId;

    @Column( name = "post_name", nullable = false )
    private String postName;

    @Lob
    @Column( name = "post_desc" )
    private String postDescription;

    @Column( name = "post_url" )
    private String postUrl;

    @Column( name = "vote_count", columnDefinition = "integer default 0")
    private Integer voteCount;

    @Column( name = "created_on" )
    @CreationTimestamp
    private Timestamp postCreatedOn;

    @Column( name = "updated_on" )
    @UpdateTimestamp
    private Timestamp postUpdatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id",nullable = false )
    private User user;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonIgnoreProperties( value = "post" )
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonIgnoreProperties( value = "post" )
    private List<Vote> votes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false )
    private Community community;

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setPost(null);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setPost(this);
    }

    public void removeVote(Vote vote) {
        votes.remove(vote);
        vote.setPost(null);
    }
}
