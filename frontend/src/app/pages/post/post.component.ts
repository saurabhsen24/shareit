import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';
import { Constants } from 'src/app/shared/constants/Constants';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import Swal from 'sweetalert2';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { VoteType } from 'src/app/shared/constants/VoteType';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  post: Post;
  currentUserName = '';
  Toast = null;

  voteType = VoteType;

  commentForm = new FormGroup({
    commentControl: new FormControl('', Validators.required),
  });

  quillConfiguration = Constants.QUILL_CONFIG;
  editorStyle = Constants.EDITOR_STYLE;

  postId: Number;
  commentId: Number;

  constructor(
    private postService: PostService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.currentUserName = this.tokenStorage.getUser()?.userName;
    this.Toast = this.messageService.getToast();
    this.route.params.subscribe((param: Params) => {
      this.postId = +param['postId'];
      this.getPost(this.postId);
    });
  }

  getPost(postId: Number) {
    this.postService.getPost(postId).subscribe(
      (post: Post) => {
        this.sharedService.changeButtonColor(post);
        let videoUrl = this.sharedService.checkIfVideoURLOrImageURL(
          post.postUrl
        );
        post.videoUrl = videoUrl;
        this.post = post;
      },
      (errResponse: ErrorResponse) => {
        this.messageService.showMessage('error', errResponse.message);
      }
    );
  }

  showNotification(notificationData: {
    icon: string;
    iconColor: string;
    text: string;
    background: string;
    color: string;
  }) {
    this.Toast.fire({
      icon: notificationData.icon,
      iconColor: notificationData.iconColor,
      text: `${notificationData.text}`,
      background: notificationData.background,
      color: notificationData.color,
    });
  }

  updatePost(postId: Number) {
    this.router.navigate(['posts', postId]);
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
        this.postService.deletePost(postId).subscribe(
          (response: GenericResponse) => {
            this.messageService.showMessage('success', response.message);
            this.router.navigateByUrl('/');
          },
          (errResponse: ErrorResponse) => {
            this.messageService.showMessage('error', errResponse.message);
          }
        );
      }
    });
  }

  vote(postId: Number, voteType: VoteType) {
    this.sharedService.vote(postId, voteType, [this.post]);
  }
}
