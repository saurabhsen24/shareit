import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { VoteType } from '../constants/VoteType';
import { Post } from '../models/Post.model';
import { ErrorResponse } from '../models/response/ErrorResponse.model';
import { VoteResponse } from '../models/response/VoteResponse.model';
import { MessageService } from './message.service';
import { TokenStorageService } from './token-storage.service';
import { VoteService } from './vote.service';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  toast = this.messageService.getToast();

  constructor(
    private voteService: VoteService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private messageService: MessageService
  ) {}

  changeButtonColor(post: Post) {
    switch (post.voteType) {
      case VoteType.UP_VOTE:
        post.upVoteColor = 'warn';
        post.downVoteColor = 'primary';
        break;
      case VoteType.DOWN_VOTE:
        post.downVoteColor = 'warn';
        post.upVoteColor = 'primary';
        break;
      default:
        post.downVoteColor = 'primary';
        post.upVoteColor = 'primary';
        break;
    }
  }

  checkIfVideoURLOrImageURL(url: string) {
    if (!url) return false;
    return url.indexOf('mp4') !== -1;
  }

  vote(postId: Number, voteType: VoteType, posts: Post[]) {
    if (!this.tokenStorage.getUser()) {
      this.router.navigateByUrl('/login');
      return;
    }

    const post = posts.find((post) => post.postId === postId);

    let voteReq = { voteType: voteType };
    this.voteService.vote(postId, voteReq).subscribe(
      (data: VoteResponse) => {
        console.log(data);
        post.voteType = data.voteType;
        post.voteCount = data.voteCount;
        this.changeButtonColor(post);
      },
      (errorResponse: ErrorResponse) => {
        this.toast.fire({
          icon: 'error',
          iconColor: 'white',
          text: `${errorResponse.message}`,
          background: '#f27474',
          color: 'white',
        });
      }
    );
  }
}
