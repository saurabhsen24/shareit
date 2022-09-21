import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/shared/constants/Constants';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { GenericResponse } from 'src/app/shared/models/response/GenericResponse.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
})
export class CreatePostComponent implements OnInit {
  editorStyle = Constants.EDITOR_STYLE;
  quillConfiguration = Constants.QUILL_CONFIG;

  postHeader = 'Create';

  isFileUploaded = false;

  file: File = null;

  isLoading = false;

  addPostForm: FormGroup = new FormGroup({
    postTitle: new FormControl('', Validators.required),
    file: new FormControl(''),
    postDescription: new FormControl('', Validators.required),
    postUrl: new FormControl(''),
  });

  postId = null;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.postId = +params.get('postId');
      if (this.postId) {
        this.postHeader = 'Update';
        this.getPost(this.postId);
      }
    });
  }

  dummy() {
    console.log(this.addPostForm.value);
  }

  onSubmit() {
    if (this.addPostForm.invalid) {
      return;
    }

    this.isLoading = true;

    if (this.postId) {
      this.postService
        .updatePost(this.postId, this.addPostForm.value)
        .subscribe(
          (response: GenericResponse) => {
            this.isLoading = false;
            this.messageService.showMessage('success', response.message);
            this.addPostForm.reset();
            this.router.navigateByUrl('/');
          },
          (errResponse: ErrorResponse) => {
            this.isLoading = false;
            this.messageService.showMessage('error', errResponse.message);
          }
        );
    } else {
      this.postService.createPost(this.addPostForm.value).subscribe(
        (response: GenericResponse) => {
          this.messageService.showMessage('success', response.message);
          this.addPostForm.reset();
          this.router.navigateByUrl('/');
        },
        (errResponse: ErrorResponse) => {
          this.messageService.showMessage('error', errResponse.message);
        },
        () => {
          this.isLoading = false;
        }
      );
    }
  }

  onChange(event) {
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
      this.isFileUploaded = true;
      this.addPostForm.patchValue({
        file: this.file,
      });
    }
  }

  discardPost() {
    Swal.fire({
      title: 'Do you want to discard post?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, discard it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.addPostForm.reset();
        this.router.navigateByUrl('/');
      }
    });
  }

  getPost(postId: Number) {
    this.isLoading = true;
    this.postService.getPost(postId).subscribe((postData: Post) => {
      this.isLoading = false;
      this.addPostForm.patchValue({
        postTitle: postData.postTitle,
        postDescription: postData.postDescription,
        postUrl: postData.postUrl,
      });
    });
  }
}
