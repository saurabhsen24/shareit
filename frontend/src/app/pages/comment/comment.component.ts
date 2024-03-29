import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Params } from '@angular/router';
import { Constants } from 'src/app/shared/constants/Constants';
import { CommentRequest } from 'src/app/shared/models/requests/CommentRequest.model';
import { CommentResponse } from 'src/app/shared/models/response/CommentResponse.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { CommentService } from 'src/app/shared/services/comment.service';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import Swal from 'sweetalert2';
import { CommentDialogComponent } from './comment-dialog/comment-dialog.component';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit {
  @Input()
  postId: Number = 0;

  @Output()
  notify = new EventEmitter<{
    icon: string;
    iconColor: string;
    text: string;
    background: string;
    color: string;
  }>();

  commentId: Number;
  comments: CommentResponse[] = [];

  quillConfiguration = Constants.QUILL_CONFIG;
  editorStyle = Constants.EDITOR_STYLE;

  currentUserName = null;

  isLoading = false;

  Toast = null;

  commentForm = new FormGroup({
    commentControl: new FormControl('', Validators.required),
  });

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
    private commentDialog: MatDialog,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit(): void {
    this.currentUserName = this.tokenStorage.getUser().userName;
    this.route.params.subscribe((param: Params) => {
      this.postId = +param['postId'];
      this.isLoading = true;
      this.getAllComments(this.postId);
    });
  }

  submitComment() {
    let commentReq: CommentRequest = {
      text: this.commentForm.get('commentControl').value,
    };

    this.isLoading = true;

    this.commentService.postComment(this.postId, commentReq).subscribe(
      (comment: CommentResponse) => {
        this.comments.push(comment);
        this.commentForm.reset();
        this.notify.emit({
          icon: 'success',
          iconColor: 'white',
          text: 'Comment Posted',
          background: '#a5dc86',
          color: 'white',
        });
      },
      (errResponse: ErrorResponse) => {
        this.notify.emit({
          icon: 'error',
          iconColor: 'white',
          text: `${errResponse.message}`,
          background: '#f27474',
          color: 'white',
        });
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  getAllComments(postId: Number) {
    this.commentService.getComments(postId).subscribe(
      (data: CommentResponse[]) => {
        this.comments = data;
      },
      (errorResponse: ErrorResponse) => {
        this.notify.emit({
          icon: 'error',
          iconColor: 'white',
          text: `${errorResponse.message}`,
          background: '#f27474',
          color: 'white',
        });
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  deleteComment(commentId: Number) {
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
        const newComments = this.comments.filter(
          (comment) => comment.commentId !== commentId
        );
        this.comments = newComments;

        this.commentService.deleteComment(commentId).subscribe(
          (res: GenericResponse) => {
            this.isLoading = false;
            this.notify.emit({
              icon: 'success',
              iconColor: 'white',
              text: `${res.message}`,
              background: '#a5dc86',
              color: 'white',
            });
          },
          (errorResponse: ErrorResponse) => {
            this.isLoading = false;
            this.notify.emit({
              icon: 'error',
              iconColor: 'white',
              text: `${errorResponse.message}`,
              background: '#f27474',
              color: 'white',
            });
          }
        );
      }
    });

  }

  openEditCommentDialog(commentId: Number) {
    this.isLoading = true;
    this.commentService.getComment(commentId).subscribe(
      (comment: CommentResponse) => {
        this.isLoading = false;
        const dialogRef = this.commentDialog.open(CommentDialogComponent, {
          data: comment,
        });
        dialogRef.afterClosed().subscribe((dataComment: CommentResponse) => {
          this.updateComment(dataComment);
        });
      },
      (errorResponse: ErrorResponse) => {
        this.isLoading = false;
        this.notify.emit({
          icon: 'error',
          iconColor: 'white',
          text: `${errorResponse.message}`,
          background: '#f27474',
          color: 'white',
        });
      }
    );
  }

  updateComment(updatedComment: CommentResponse) {
    if (!updatedComment) return;
    this.isLoading = true;
    const index = this.comments.findIndex(
      (c) => c.commentId === updatedComment.commentId
    );
    const updatedComments = this.comments;
    updatedComments[index] = updatedComment;

    this.comments = updatedComments;

    this.isLoading = false;

    this.notify.emit({
      icon: 'success',
      iconColor: 'white',
      text: 'Comment Updated',
      background: '#a5dc86',
      color: 'white',
    });
  }
}
