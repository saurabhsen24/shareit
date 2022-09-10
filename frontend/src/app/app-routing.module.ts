import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { ForgetPasswordComponent } from "./auth/forget-password/forget-password.component";
import { LoginComponent } from "./auth/login/login.component";
import { ResetPasswordComponent } from "./auth/reset-password/reset-password.component";
import { SignupComponent } from "./auth/signup/signup.component";
import { PostComponent } from "./pages/post/post.component";
import { ViewPostComponent } from "./pages/view-post/view-post.component";

const routes: Routes = [
  { path: "", component: ViewPostComponent },
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },
  { path: "forgetPassword", component: ForgetPasswordComponent },
  { path: "resetPassword", component: ResetPasswordComponent },
  { path: "post/:id", component: PostComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
