import { Component, OnInit } from "@angular/core";
import { Post } from "src/app/shared/models/Post.model";

@Component({
  selector: "app-view-post",
  templateUrl: "./view-post.component.html",
  styleUrls: ["./view-post.component.css"],
})
export class ViewPostComponent implements OnInit {
  userProfileImage =
    "https://www.flaticon.com/free-icon/user_149071?term=user&page=1&position=8&page=1&position=8&related_id=149071&origin=search";

  posts: Post[] = [
    {
      postTitle: "Comedy Show",
      postDescription:
        "Bro this man is actual underrated standup gem... rewatched today and laughed as much as i did the first time and also as much as the audience😳",
      postId: 1,
      postUrl:
        "https://preview.redd.it/cdj44k37c3m91.png?width=640&crop=smart&auto=webp&s=02338df4afdcfc648619b5f248148533af492c96",
      voteCount: 0,
      userName: "Saurabh Sen",
    },
    {
      postTitle: "Comedy Show",
      postDescription:
        "Bro this man is actual underrated standup gem... rewatched today and laughed as much as i did the first time and also as much as the audience😳",
      postId: 1,
      postUrl:
        "https://preview.redd.it/cdj44k37c3m91.png?width=640&crop=smart&auto=webp&s=02338df4afdcfc648619b5f248148533af492c96",
      voteCount: 0,
      userName: "Saurabh Sen",
    },
    {
      postTitle: "Comedy Show",
      postDescription:
        "Bro this man is actual underrated standup gem... rewatched today and laughed as much as i did the first time and also as much as the audience😳",
      postId: 1,
      postUrl:
        "https://preview.redd.it/cdj44k37c3m91.png?width=640&crop=smart&auto=webp&s=02338df4afdcfc648619b5f248148533af492c96",
      voteCount: 0,
      userName: "Saurabh Sen",
    },
    {
      postTitle: "Comedy Show",
      postDescription:
        "Bro this man is actual underrated standup gem... rewatched today and laughed as much as i did the first time and also as much as the audience😳",
      postId: 1,
      postUrl:
        "https://preview.redd.it/cdj44k37c3m91.png?width=640&crop=smart&auto=webp&s=02338df4afdcfc648619b5f248148533af492c96",
      voteCount: 0,
      userName: "Saurabh Sen",
    },
    {
      postTitle: "Comedy Show",
      postDescription:
        "Bro this man is actual underrated standup gem... rewatched today and laughed as much as i did the first time and also as much as the audience😳",
      postId: 1,
      postUrl:
        "https://preview.redd.it/cdj44k37c3m91.png?width=640&crop=smart&auto=webp&s=02338df4afdcfc648619b5f248148533af492c96",
      voteCount: 0,
      userName: "Saurabh Sen",
    },
  ];

  constructor() {}

  ngOnInit() {}
}
