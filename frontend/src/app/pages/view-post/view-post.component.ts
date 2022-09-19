import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VoteType } from 'src/app/shared/constants/VoteType';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css'],
})
export class ViewPostComponent implements OnInit {
  posts: Post[] = [];

  voteType = VoteType;

  currentUserName = '';

  isLoading = false;

  constructor(
    private postService: PostService,
    private messageService: MessageService,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.currentUserName = this.tokenStorage.getUser()?.userName;
    this.getAllPosts();
  }

  getAllPosts() {
    this.isLoading = true;

    this.postService.getAllPosts().subscribe(
      (postData: Post[]) => {
        postData.map((post) => {
          this.sharedService.changeButtonColor(post);
          let videoUrl = this.sharedService.checkIfVideoURLOrImageURL(
            post.postUrl
          );

          post.videoUrl = videoUrl;
        });
        this.posts = postData;
      },
      (err: ErrorResponse) => {
        this.messageService.showMessage('error', err.message);
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  deletePost(postId: Number) {
    const newPosts = this.posts.filter((post) => post.postId !== postId);
    this.posts = newPosts;

    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.isLoading = true;
        const posts = this.posts.filter((post) => post.postId !== postId);
        this.posts = posts;
        this.postService.deletePost(postId).subscribe(
          (response: GenericResponse) => {
            this.messageService.showMessage('success', response.message);
          },
          (errResponse: ErrorResponse) => {
            this.messageService.showMessage('error', errResponse.message);
          },
          () => {
            this.isLoading = false;
          }
        );
      }
    });
  }

  updatePost(postId: Number) {
    this.router.navigate(['posts', postId]);
  }

  vote(postId: Number, voteType: VoteType) {
    this.sharedService.vote(postId, voteType, this.posts);
  }
}
