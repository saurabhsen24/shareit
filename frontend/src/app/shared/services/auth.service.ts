import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { LoginRequest } from "../models/requests/LoginRequest.model";
import { ForgetPasswordRequest } from "../models/requests/ForgetPasswordRequest.model";
import { SignupRequest } from "../models/requests/SignupRequest.model";
import { ResetPasswordRequest } from "../models/requests/ResetPasswordRequest.model";
import { Observable, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { LoginResponse } from "../models/response/LoginResponse.model";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) {}

  private baseUrl = environment.API_URL;

  private authApi = this.baseUrl + "/auth";

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.authApi}/login`, loginRequest)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  forgetPassword(forgetPasswordRequest: ForgetPasswordRequest) {
    return this.http
      .post(`${this.authApi}/forgetPassword`, forgetPasswordRequest)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  signup(signupRequest: SignupRequest) {
    return this.http.post(`${this.authApi}/signup`, signupRequest).pipe(
      tap((response) => console.log(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  resetPassword(resetPasswordRequest: ResetPasswordRequest) {
    return this.http
      .put(`${this.authApi}/resetPassword`, resetPasswordRequest)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
