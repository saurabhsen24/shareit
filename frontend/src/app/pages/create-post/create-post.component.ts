import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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

  addPostForm: FormGroup = new FormGroup({
    postTitle: new FormControl('', Validators.required),
    postDescription: new FormControl('', Validators.required),
    postUrl: new FormControl(''),
  });

  constructor(
    private postService: PostService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  onSubmit() {
    if (this.addPostForm.invalid) {
      return;
    }

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
