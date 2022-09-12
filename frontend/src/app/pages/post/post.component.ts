import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { CommentRequest } from 'src/app/shared/models/requests/CommentRequest.model';
import { CommentService } from 'src/app/shared/services/comment.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';
import { CommentResponse } from 'src/app/shared/models/response/CommentResponse.model';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  post: Post;
  comments: CommentResponse[] = [];

  commentForm = new FormGroup({
    commentControl: new FormControl('', Validators.required),
  });

  editorStyle = {
    height: '100px',
  };

  quillConfiguration = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'], // toggled buttons
      ['blockquote', 'code-block'],

      [{ header: 1 }, { header: 2 }], // custom button values
      [{ list: 'ordered' }, { list: 'bullet' }],
      [{ script: 'sub' }, { script: 'super' }], // superscript/subscript
      [{ indent: '-1' }, { indent: '+1' }], // outdent/indent
      [{ direction: 'rtl' }], // text direction

      [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
      [{ header: [1, 2, 3, 4, 5, 6, false] }],

      [{ color: [] }, { background: [] }], // dropdown with defaults from theme
      [{ font: [] }],
      [{ align: [] }],

      ['clean'], // remove formatting button

      // ['link', 'image', 'video'], // link and image, video
    ],
  };

  postId: Number;

  constructor(
    private postService: PostService,
    private messageService: MessageService,
    private commentService: CommentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe((param: Params) => {
      this.postId = +param['postId'];
      this.getPost(this.postId);
      this.getAllComments(this.postId);
    });
  }

  submitComment() {
    let commentReq: CommentRequest = {
      text: this.commentForm.get('commentControl').value,
    };

    this.commentService.postComment(this.postId, commentReq).subscribe(
      (response: GenericResponse) => {
        this.messageService.showMessage('success', response.message);
        this.commentForm.reset();
      },
      (errResponse: ErrorResponse) => {
        this.messageService.showMessage('error', errResponse.message);
      }
    );
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

  getAllComments(postId: Number) {
    const Toast = this.messageService.getToast();
    this.commentService.getComments(postId).subscribe(
      (data: CommentResponse[]) => {
        this.comments = data;
      },
      (errorResponse: ErrorResponse) => {
        Toast.fire({
          icon: 'error',
          iconColor: 'white',
          text: `${errorResponse.message}`,
          background: '#f27474',
          color: 'white',
        });
      }
    );
  }

  deleteComment(postId: Number, commentId: Number) {
    const newComments = this.comments.filter(
      (comment) => comment.commentId !== commentId
    );
    this.comments = newComments;
    const Toast = this.messageService.getToast();
    this.commentService.deleteComment(postId, commentId).subscribe(
      (res: GenericResponse) => {
        Toast.fire({
          icon: 'success',
          iconColor: 'white',
          text: `${res.message}`,
          background: '#a5dc86',
          color: 'white',
        });
      },
      (errorResponse: ErrorResponse) => {
        Toast.fire({
          icon: 'error',
          iconColor: 'white',
          text: `${errorResponse.message}`,
          background: '#f27474',
          color: 'white',
        });
      }
    );
  }
}
