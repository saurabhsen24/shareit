import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { ForgetPasswordComponent } from "./auth/forget-password/forget-password.component";
import { LoginComponent } from "./auth/login/login.component";
import { ResetPasswordComponent } from "./auth/reset-password/reset-password.component";
import { SignupComponent } from "./auth/signup/signup.component";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },
  { path: "forgetPassword", component: ForgetPasswordComponent },
  { path: "resetPassword", component: ResetPasswordComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
