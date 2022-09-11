import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { Post } from 'src/app/shared/models/Post.model';
import { ErrorResponse } from 'src/app/shared/models/response/ErrorResponse.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { PostService } from 'src/app/shared/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  post: Post;

  commentForm = new FormGroup({
    commentControl: new FormControl('', Validators.required),
  });

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

      // ['link', 'image', 'video'], // link and image, video
    ],
  };

  commentText = '';

  constructor(
    private postService: PostService,
    private messageService: MessageService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe((param: Params) => {
      let postId = +param['postId'];
      console.log('postId from url: ' + param['postId']);
      this.getPost(postId);
    });
  }

  submitComment() {
    console.log(this.commentForm);
  }

  getPost(postId: Number) {
    this.postService.getPost(postId).subscribe(
      (post: Post) => {
        this.post = post;
      },
      (errResponse: ErrorResponse) => {
        this.messageService.showMessage('error', errResponse.message);
      }
    );
  }
}
