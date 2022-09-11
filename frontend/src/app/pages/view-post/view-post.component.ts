import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css'],
})
export class ViewPostComponent implements OnInit {
  posts: Post[] = [];

  constructor(
    private postService: PostService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.getAllPosts();
  }

  getAllPosts() {
    this.postService.getAllPosts().subscribe(
      (postData: Post[]) => {
        this.posts = postData;
      },
      (err: ErrorResponse) => {
        this.messageService.showMessage('error', err.message);
      }
    );
  }

  deletePost(postId: Number) {
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
        const posts = this.posts.filter((post) => post.postId !== postId);
        this.posts = posts;
        this.postService.deletePost(postId).subscribe(
          (response: GenericResponse) => {
            this.messageService.showMessage('success', response.message);
          },
          (errResponse: ErrorResponse) => {
            this.messageService.showMessage('error', errResponse.message);
          }
        );
      }
    });
  }

  updatePost(postId: Number) {
    this.router.navigate(['posts', postId]);
  }
}
