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
        tap((res) => console.debug(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  postComment(postId: Number, commetRequest: CommentRequest) {
    return this.http
      .post<CommentResponse>(
        `${this.commentApi}/${postId}/createComment`,
        commetRequest
      )
      .pipe(
        tap((res) => console.debug(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  deleteComment(commentId: Number) {
    return this.http
      .delete<GenericResponse>(`${this.commentApi}/deleteComment/${commentId}`)
      .pipe(
        tap((res) => console.debug(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  updateComment(commentId: Number, commentRequest: CommentRequest) {
    return this.http
      .patch<CommentResponse>(
        `${this.commentApi}/editComment/${commentId}`,
        commentRequest
      )
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  getComment(commentId: Number) {
    return this.http.get<CommentResponse>(`${this.commentApi}/${commentId}`);
  }
}
