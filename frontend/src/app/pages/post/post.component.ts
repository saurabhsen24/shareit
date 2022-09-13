import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';
import { Constants } from 'src/app/shared/constants/Constants';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  post: Post;

  Toast = null;

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
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.Toast = this.messageService.getToast();
    this.route.params.subscribe((param: Params) => {
      this.postId = +param['postId'];
      this.getPost(this.postId);
    });
  }

  getPost(postId: Number) {
    this.postService.getPost(postId).subscribe(
      (post: Post) => {
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
}
