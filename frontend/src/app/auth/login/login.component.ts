import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { ForgetPasswordRequest } from "src/app/shared/models/requests/ForgetPasswordRequest.model";
import { LoginRequest } from "src/app/shared/models/requests/LoginRequest.model";
import { ErrorResponse } from "src/app/shared/models/response/ErrorResponse.model";
import { GenericResponse } from "src/app/shared/models/response/GenericResponse.model";
import { LoginResponse } from "src/app/shared/models/response/LoginResponse.model";
import { AuthService } from "src/app/shared/services/auth.service";
import { MessageService } from "src/app/shared/services/message.service";
import { TokenStorageService } from "src/app/shared/services/token-storage.service";
import Swal from "sweetalert2";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginRequest: LoginRequest;

  constructor(
    private authService: AuthService,
    private messageService: MessageService,
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),
    });
  }

  onSubmit() {
    const Toast = this.messageService.getToast();

    if (this.loginForm.invalid) {
      Toast.fire({
        icon: "error",
        iconColor: "white",
        text: "Invalid Username or Password",
        background: "#f27474",
        color: "white",
      });
      return;
    }

    this.authService.login(this.loginForm.value).subscribe(
      (loginResponse: LoginResponse) => {
        console.log(loginResponse);
        this.tokenStorage.saveUser({
          ...loginResponse,
        });

        this.router.navigateByUrl("/");
        this.loginForm.reset();
      },
      (err: ErrorResponse) => {
        Toast.fire({
          icon: "error",
          iconColor: "white",
          text: `${err.message}`,
          background: "#f27474",
          color: "white",
        });
      },
      () => {
        Toast.fire({
          icon: "success",
          iconColor: "white",
          text: "Login Successfully",
          background: "#a5dc86",
          color: "white",
        });
      }
    );
  }

  forgetPassword() {
    let responseMessage = "";
    let forgetPasswordObs = null;

    Swal.fire({
      title: "Please enter your email",
      input: "email",
      inputPlaceholder: "Enter your email address",
      showCancelButton: true,
      confirmButtonText: "Send OTP",
      showLoaderOnConfirm: true,
      preConfirm: (email: string) => {
        let forgetPasswordRequest: ForgetPasswordRequest = {
          email: `${email}`,
        };
        forgetPasswordObs = this.authService.generateOTP(forgetPasswordRequest);
      },
      allowOutsideClick: () => !Swal.isLoading(),
      backdrop: true,
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: "Sending Email",
        });

        Swal.showLoading();

        forgetPasswordObs.subscribe(
          (response: GenericResponse) => {
            Swal.close();
            responseMessage = response.message;
          },
          (err: ErrorResponse) => {
            console.log(err);
            responseMessage = err.message;
            this.messageService.showMessage("info", responseMessage);
          },
          () => {
            console.log("Done");
            this.messageService.showMessage("success", responseMessage);
          }
        );
      }
    });
  }
}
