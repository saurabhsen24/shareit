import { Injectable } from "@angular/core";
import { Constants } from "../constants/Constants";

@Injectable({
  providedIn: "root",
})
export class TokenStorageService {
  constructor() {}

  logOut(): void {
    window.sessionStorage.clear();
  }

  saveUser(userData: any) {
    window.sessionStorage.removeItem(Constants.USER_KEY);
    window.sessionStorage.setItem(Constants.USER_KEY, JSON.stringify(userData));
  }

  getUser() {
    const user = window.sessionStorage.getItem(Constants.USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }
}
