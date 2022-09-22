import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDetails } from 'src/app/shared/models/response/UserDetails.model';
import { UserService } from 'src/app/shared/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
})
export class UpdateUserComponent implements OnInit {
  isLoading: boolean = false;
  updateUserForm: FormGroup;

  username: string = '';
  userProfilePic: string = '';

  userData: UserDetails = null;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.updateUserForm = new FormGroup({
      college: new FormControl(''),
      countryName: new FormControl(''),
      currentCity: new FormControl(''),
      email: new FormControl(''),
      gender: new FormControl(''),
      homeTown: new FormControl(''),
      worksAt: new FormControl(''),
    });

    this.route.paramMap.subscribe((params) => {
      this.username = params.get('username');
      this.getUserDetails(this.username);
    });
  }

  onSubmit() {
    this.userService
      .updateUserDetails(this.updateUserForm.value)
      .subscribe((data) => {
        console.log(data);
        this.router.navigate(['/user/' + this.username]);
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
        this.setUserDetails();
      });
  }

  setUserDetails() {
    let userDetails = {
      college: this.userData.college ? this.userData.college : '',
      countryName: this.userData.countryName ? this.userData.countryName : '',
      currentCity: this.userData.currentCity ? this.userData.currentCity : '',
      email: this.userData.email ? this.userData.email : '',
      gender: this.userData.gender ? this.userData.gender : '',
      homeTown: this.userData.homeTown ? this.userData.homeTown : '',
      worksAt: this.userData.worksAt ? this.userData.worksAt : '',
    };
    this.updateUserForm.patchValue(userDetails);
  }
}
