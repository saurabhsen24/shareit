import { HttpClient } from '@angular/common/http';
import { catchError, tap, throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { GenericResponse } from '../models/response/GenericResponse.model';
import { UserDetails } from '../models/response/UserDetails.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = environment.API_URL;
  private userApi = this.baseUrl + '/user';

  constructor(private http: HttpClient) {}

  uploadPic(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http
      .post<GenericResponse>(`${this.userApi}/upload`, formData)
      .pipe(
        tap((res) => console.debug(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  getUserDetails(username: string) {
    return this.http.get<UserDetails>(`${this.userApi}/${username}`).pipe(
      tap((res) => console.debug(res)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  updateUserDetails(userReq: any) {
    return this.http.patch(`${this.userApi}/update`, userReq);
  }
}
