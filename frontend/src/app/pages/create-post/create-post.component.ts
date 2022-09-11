import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
  editorStyle = {
    height: '100px',
  };

  quillConfiguration = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'], // toggled buttons
      ['blockquote', 'code-block'],

      [{ header: 1 }, { header: 2 }], // custom button values
      [{ list: 'ordered' }, { list: 'bullet' }],
      [{ script: 'sub' }, { script: 'super' }], // superscript/subscript
      [{ indent: '-1' }, { indent: '+1' }], // outdent/indent
      [{ direction: 'rtl' }], // text direction

      [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
      [{ header: [1, 2, 3, 4, 5, 6, false] }],

      [{ color: [] }, { background: [] }], // dropdown with defaults from theme
      [{ font: [] }],
      [{ align: [] }],

      ['clean'], // remove formatting button
    ],
  };

  postHeader = 'Create';

  addPostForm: FormGroup = new FormGroup({
    postTitle: new FormControl('', Validators.required),
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
      }
      this.postService.getPost(this.postId).subscribe((postData: Post) => {
        this.addPostForm.patchValue({
          postTitle: postData.postTitle,
          postDescription: postData.postDescription,
          postUrl: postData.postUrl,
        });
      });
    });
  }

  onSubmit() {
    if (this.addPostForm.invalid) {
      return;
    }

    if (this.postId) {
      this.postService
        .updatePost(this.postId, this.addPostForm.value)
        .subscribe(
          (response: GenericResponse) => {
            this.messageService.showMessage('success', response.message);
            this.addPostForm.reset();
          },
          (errResponse: ErrorResponse) => {
            this.messageService.showMessage('error', errResponse.message);
          }
        );
    } else {
      this.postService.createPost(this.addPostForm.value).subscribe(
        (response: GenericResponse) => {
          this.messageService.showMessage('success', response.message);
          this.addPostForm.reset();
        },
        (errResponse: ErrorResponse) => {
          this.messageService.showMessage('error', errResponse.message);
        }
      );
    }
    this.router.navigateByUrl('/');
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
      }
    });
  }
}
