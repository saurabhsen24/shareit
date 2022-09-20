import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Post } from '../models/Post.model';
import { PostRequest } from '../models/requests/PostRequest.model';
import { GenericResponse } from '../models/response/GenericResponse.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private baseUrl = environment.API_URL;
  private postApi = this.baseUrl + '/post';

  constructor(private http: HttpClient) {}

  getPost(postId: Number) {
    return this.http.get<Post>(`${this.postApi}/${postId}`).pipe(
      tap((res) => console.log(res)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  getAllPosts() {
    return this.http.get<Post[]>(`${this.postApi}/posts`).pipe(
      tap((response) => console.log(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  getAllPostsByUser(username: string){
    return this.http.get<Post[]>(`${this.postApi}/posts/${username}`).pipe(
      tap((response) => console.log(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  createPost(postRequest: PostRequest) {
    const formData = new FormData();

    let postReq = {
      postTitle: postRequest.postTitle,
      postDescription: postRequest.postDescription,
    };

    let file = postRequest.file;

    const blob = new Blob([JSON.stringify(postReq)], {
      type: 'application/json',
    });

    formData.append('postRequest', blob);
    formData.append('file', file);

    return this.http
      .post<GenericResponse>(`${this.postApi}/createPost`, formData)
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  deletePost(postId: Number) {
    return this.http.delete<GenericResponse>(`${this.postApi}/${postId}`).pipe(
      tap((res) => console.log(res)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  updatePost(postId: Number, postRequest: PostRequest) {
    const formData = new FormData();

    let postReq = {
      postTitle: postRequest.postTitle,
      postDescription: postRequest.postDescription,
    };

    let file = postRequest.file;

    const blob = new Blob([JSON.stringify(postReq)], {
      type: 'application/json',
    });

    formData.append('postRequest', blob);
    formData.append('file', file);

    return this.http
      .put<GenericResponse>(`${this.postApi}/updatePost/${postId}`, formData)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
