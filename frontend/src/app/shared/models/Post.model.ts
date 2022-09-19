import { VoteType } from '../constants/VoteType';

export interface Post {
  postTitle: string;
  postDescription: string;
  postId: Number;
  postUrl: string;
  voteCount: Number;
  userName: string;
  voteType: VoteType;
  upVoteColor: string;
  downVoteColor: string;
  videoUrl?: boolean;
}
