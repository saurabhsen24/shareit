import { Component, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDetails } from 'src/app/shared/models/response/UserDetails.model';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import { UserService } from 'src/app/shared/services/user.service';
import { environment } from 'src/environments/environment';
import { UploadPicComponent } from './upload-pic/upload-pic.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  @Output()
  username: string;

  userProfilePic: string = '';

  userData: UserDetails = null;

  isLoading = false;

  loggedInUser = null;

  constructor(
    private route: ActivatedRoute,
    private uploadPicDialog: MatDialog,
    private userService: UserService,
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loggedInUser = this.tokenStorage.getUser()?.userName;

    this.route.paramMap.subscribe((params) => {
      this.username = params.get('username');
      this.getUserDetails(this.username);
    });

    this.userProfilePic = environment.defaultUserProfile;
  }

  uploadProfile() {
    this.uploadPicDialog
      .open(UploadPicComponent, { data: this.userProfilePic })
      .afterClosed()
      .subscribe((res) => {
        this.userProfilePic = res.data;
      });
  }

  getUserDetails(username: string) {
    this.userService
      .getUserDetails(username)
      .subscribe((userData: UserDetails) => {
        console.log(userData);
        this.userProfilePic = userData.userProfilePic
          ? userData.userProfilePic
          : environment.defaultUserProfile;
        this.userData = userData;
      });
  }

  updateUser() {
    const updateUserURL = '/user/' + this.username + '/update';
    this.router.navigateByUrl(updateUserURL);
  }
}
