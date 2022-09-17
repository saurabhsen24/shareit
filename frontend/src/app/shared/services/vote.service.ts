import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { VoteRequest } from '../models/requests/VoteRequest.model';
import { VoteResponse } from '../models/response/VoteResponse.model';

@Injectable({
  providedIn: 'root',
})
export class VoteService {
  private baseUrl = environment.API_URL;
  private voteApi = this.baseUrl + '/vote';

  constructor(private http: HttpClient) {}

  vote(postId: Number, voteRequest: VoteRequest) {
    return this.http
      .post<VoteResponse>(`${this.voteApi}/${postId}`, voteRequest)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
