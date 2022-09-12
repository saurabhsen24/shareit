import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommentRequest } from '../models/requests/CommentRequest.model';
import { CommentResponse } from '../models/response/CommentResponse.model';
import { GenericResponse } from '../models/response/GenericResponse.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl = environment.API_URL;
  private commentApi = this.baseUrl + '/comment';

  constructor(private http: HttpClient) {}

  getComments(postId: Number) {
    return this.http
      .get<CommentResponse[]>(`${this.commentApi}/${postId}/comments`)
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  postComment(postId: Number, commetRequest: CommentRequest) {
    return this.http
      .post<GenericResponse>(
        `${this.commentApi}/${postId}/createComment`,
        commetRequest
      )
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  deleteComment(postId: Number, commentId: Number) {
    return this.http
      .delete<GenericResponse>(
        `${this.commentApi}/${postId}/deleteComment/${commentId}`
      )
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  updateComment(
    postId: Number,
    commentId: Number,
    commentRequest: CommentRequest
  ) {
    return this.http
      .patch(
        `${this.commentApi}/${postId}/editComment/${commentId}`,
        commentRequest
      )
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
