import { Component, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
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

  loggedInUser = null;

  constructor(
    private route: ActivatedRoute,
    private uploadPicDialog: MatDialog,
    private userService: UserService,
    private tokenStorage: TokenStorageService
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
}
