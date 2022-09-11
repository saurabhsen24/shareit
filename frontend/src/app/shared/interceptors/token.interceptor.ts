import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { catchError, Observable, of, throwError } from 'rxjs';
import { UserData } from '../models/UserData.model';
import { TokenStorageService } from '../services/token-storage.service';
import { AuthService } from '../services/auth.service';
const TOKEN_HEADER_KEY = 'Authorization';
const BEARER_TOKEN = 'Bearer';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  isRefreshing = false;

  constructor(
    private tokenStorage: TokenStorageService,
    private authService: AuthService
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (
      request.url.indexOf('auth') !== -1 ||
      request.url.indexOf('/posts') !== -1
    ) {
      return next.handle(request);
    }

    const user: UserData = this.tokenStorage.getUser();

    if (user.accessToken != null) {
      request = this.addTokenHeader(request, user.accessToken);
    }

    return next.handle(request);
  }

  private addTokenHeader(request: HttpRequest<unknown>, token: string) {
    return request.clone({
      headers: request.headers.set(
        TOKEN_HEADER_KEY,
        `${BEARER_TOKEN} ${token}`
      ),
    });
  }
}
