import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { ErrorResponse } from "src/app/shared/models/response/ErrorResponse.model";
import { GenericResponse } from "src/app/shared/models/response/GenericResponse.model";
import { AuthService } from "src/app/shared/services/auth.service";
import { MessageService } from "src/app/shared/services/message.service";
import Swal from "sweetalert2";

@Component({
  selector: "app-forget-password",
  templateUrl: "./forget-password.component.html",
  styleUrls: ["./forget-password.component.css"],
})
export class ForgetPasswordComponent implements OnInit {
  forgetPasswordForm: FormGroup;

  constructor(
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router
  ) {
    this.forgetPasswordForm = new FormGroup({
      email: new FormControl("", [Validators.email, Validators.required]),
    });
  }

  ngOnInit() {}

  onSubmit() {
    this.authService.forgetPassword(this.forgetPasswordForm.value).subscribe(
      (response: GenericResponse) => {
        console.log(response);
        this.messageService.showMessage("success", response.message);
        this.router.navigateByUrl("/resetPassword");
      },
      (err: ErrorResponse) => {
        console.log(err);
        this.messageService.showMessage("error", err.message);
      },
      () => {
        console.log("Done");
      }
    );
  }
}
