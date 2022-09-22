import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForgetPasswordComponent } from './auth/forget-password/forget-password.component';
import { LoginComponent } from './auth/login/login.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { SignupComponent } from './auth/signup/signup.component';
import { CommentComponent } from './pages/comment/comment.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { PostComponent } from './pages/post/post.component';
import { UpdateUserComponent } from './pages/user/update-user/update-user.component';
import { UserComponent } from './pages/user/user.component';
import { ViewPostComponent } from './pages/view-post/view-post.component';
import { AuthGuard } from './shared/gaurds/auth.guard';

const routes: Routes = [
  { path: '', component: ViewPostComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'forgetPassword', component: ForgetPasswordComponent },
  { path: 'resetPassword', component: ResetPasswordComponent },
  {
    path: 'createPost',
    component: CreatePostComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'updatePost/:postId',
    component: CreatePostComponent,
    canActivate: [AuthGuard],
  },
  { path: 'post/:postId', component: PostComponent, canActivate: [AuthGuard] },
  {
    path: 'post/:postId/comment/:commentId',
    component: CommentComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user/:username',
    component: UserComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user/:username/update',
    component: UpdateUserComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
