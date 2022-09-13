import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Constants } from 'src/app/shared/constants/Constants';
import { CommentResponse } from 'src/app/shared/models/response/CommentResponse.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { CommentService } from 'src/app/shared/services/comment.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-comment-dialog',
  templateUrl: './comment-dialog.component.html',
  styleUrls: ['./comment-dialog.component.css'],
})
export class CommentDialogComponent implements OnInit {
  quillConfiguration = Constants.QUILL_CONFIG;
  editorStyle = Constants.EDITOR_STYLE;

  commentForm = new FormGroup({
    commentText: new FormControl(this.data.commentText, Validators.required),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: CommentResponse,
    private dialog: MatDialogRef<CommentDialogComponent>,
    private commentService: CommentService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  updateComment(commentId: Number) {
    this.commentService
      .updateComment(commentId, {
        text: this.commentForm.get('commentText').value,
      })
      .subscribe(
        (updatedComment: CommentResponse) => {
          this.dialog.close(updatedComment);
        },
        (errorResponse: ErrorResponse) => {
          this.messageService.showMessage('error', errorResponse.message);
        }
      );
  }
}
