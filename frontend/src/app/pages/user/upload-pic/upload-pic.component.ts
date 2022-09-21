import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { UserService } from 'src/app/shared/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-upload-pic',
  templateUrl: './upload-pic.component.html',
  styleUrls: ['./upload-pic.component.css'],
})
export class UploadPicComponent implements OnInit {
  photoPreviewUrl = '';
  file: File = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public profilePic: string,
    private dialogRef: MatDialogRef<UploadPicComponent>,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.photoPreviewUrl = this.profilePic
      ? this.profilePic
      : environment.defaultUserProfile;
  }

  savePhoto() {
    this.userService.uploadPic(this.file).subscribe((data: GenericResponse) => {
      this.dialogRef.close({ data: data.message });
    });
  }

  onChange(event) {
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(this.file);
      reader.onload = (e: any) => {
        this.photoPreviewUrl = e.target.result;
      };
    }
  }
}
