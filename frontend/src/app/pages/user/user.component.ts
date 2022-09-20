import { Component, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { UploadPicComponent } from './upload-pic/upload-pic.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  @Output()
  username: string;

  constructor(
    private route: ActivatedRoute,
    private uploadPicDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.username = params.get('username');
    });
  }

  uploadProfile() {
    let profilePic = '';

    this.uploadPicDialog
      .open(UploadPicComponent, { data: profilePic })
      .afterClosed()
      .subscribe((file: File) => {
        console.log(file);
      });
  }
}
