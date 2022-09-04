import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { ErrorResponse } from "src/app/shared/models/response/ErrorResponse.model";
import { GenericResponse } from "src/app/shared/models/response/GenericResponse.model";
import { AuthService } from "src/app/shared/services/auth.service";
import { MessageService } from "src/app/shared/services/message.service";

@Component({
  selector: "app-reset-password",
  templateUrl: "./reset-password.component.html",
  styleUrls: ["./reset-password.component.css"],
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm: FormGroup;

  constructor(
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.resetPasswordForm = new FormGroup({
      email: new FormControl("", Validators.required),
      newPassword: new FormControl("", Validators.required),
      otp: new FormControl("", Validators.required),
    });
  }

  onSubmit() {
    const Toast = this.messageService.getToast();
    let responseMessage = "";

    if (this.resetPasswordForm.invalid) {
      Toast.fire({
        icon: "error",
        iconColor: "white",
        text: "Invalid Password",
        background: "#f27474",
        color: "white",
      });
      return;
    }

    this.authService.resetPassword(this.resetPasswordForm.value).subscribe(
      (response: GenericResponse) => {
        console.log(response);
        responseMessage = response.message;
        this.resetPasswordForm.reset();
        Toast.fire({
          icon: "success",
          iconColor: "white",
          text: `${responseMessage}`,
          background: "#a5dc86",
          color: "white",
        });
        this.router.navigateByUrl("/login");
      },
      (err: ErrorResponse) => {
        Toast.fire({
          icon: "error",
          iconColor: "white",
          text: `${err.message}`,
          background: "#f27474",
          color: "white",
        });
      }
    );
  }

  forgetPassword() {
    this.router.navigateByUrl("/forgetPassword");
  }
}
