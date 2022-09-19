import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../shared/services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  isLoggedIn: Boolean = false;

  username: string;

  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.autoLogin();

    this.tokenStorage.authStatusListener.subscribe((data) => {
      this.isLoggedIn = data;
    });
  }

  autoLogin() {
    const user = this.tokenStorage.getUser();
    if (user) {
      this.isLoggedIn = true;
      this.username = user.userName;
      this.tokenStorage.authStatusListener.next(true);
    } else {
      this.isLoggedIn = false;
      this.tokenStorage.authStatusListener.next(false);
    }
  }

  logOutUser() {
    this.tokenStorage.logOut();
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.tokenStorage.authStatusListener.unsubscribe();
  }
}
