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

  Toast = null;

  commentForm = new FormGroup({
    commentControl: new FormControl('', Validators.required),
  });

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
    private commentDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((param: Params) => {
      this.postId = +param['postId'];
      this.getAllComments(this.postId);
    });
  }

  submitComment() {
    let commentReq: CommentRequest = {
      text: this.commentForm.get('commentControl').value,
    };

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
      }
    );
  }

  deleteComment(commentId: Number) {
    const newComments = this.comments.filter(
      (comment) => comment.commentId !== commentId
    );
    this.comments = newComments;

    this.commentService.deleteComment(commentId).subscribe(
      (res: GenericResponse) => {
        this.notify.emit({
          icon: 'success',
          iconColor: 'white',
          text: `${res.message}`,
          background: '#a5dc86',
          color: 'white',
        });
      },
      (errorResponse: ErrorResponse) => {
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

  openEditCommentDialog(commentId: Number) {
    this.commentService.getComment(commentId).subscribe(
      (comment: CommentResponse) => {
        const dialogRef = this.commentDialog.open(CommentDialogComponent, {
          data: comment,
        });
        dialogRef.afterClosed().subscribe((dataComment: CommentResponse) => {
          this.updateComment(dataComment);
        });
      },
      (errorResponse: ErrorResponse) => {
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
    const index = this.comments.findIndex(
      (c) => c.commentId === updatedComment.commentId
    );
    const updatedComments = this.comments;
    updatedComments[index] = updatedComment;

    this.comments = updatedComments;

    this.notify.emit({
      icon: 'success',
      iconColor: 'white',
      text: 'Comment Updated',
      background: '#a5dc86',
      color: 'white',
    });
  }
}
