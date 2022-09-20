import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-upload-pic',
  templateUrl: './upload-pic.component.html',
  styleUrls: ['./upload-pic.component.css'],
})
export class UploadPicComponent implements OnInit {
  photoPreviewUrl = '';
  file: File;

  constructor(
    @Inject(MAT_DIALOG_DATA) public profilePic: string,
    private dialogRef: MatDialogRef<UploadPicComponent>
  ) {}

  ngOnInit(): void {
    this.photoPreviewUrl = this.profilePic
      ? this.profilePic
      : environment.defaultUserProfile;
  }

  savePhoto() {
    this.dialogRef.close({ file: this.file });
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
