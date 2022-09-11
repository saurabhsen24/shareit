import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Constants } from '../constants/Constants';
import { LoginResponse } from '../models/response/LoginResponse.model';
import { UserData } from '../models/UserData.model';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  public authStatusListener = new Subject<boolean>();

  constructor() {}

  logOut(): void {
    sessionStorage.clear();
    this.authStatusListener.next(false);
  }

  saveUser(userData: LoginResponse) {
    sessionStorage.removeItem(Constants.USER_KEY);
    sessionStorage.setItem(Constants.USER_KEY, JSON.stringify(userData));
    this.authStatusListener.next(true);
  }

  getUser(): UserData {
    const user = sessionStorage.getItem(Constants.USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return null;
  }
}
