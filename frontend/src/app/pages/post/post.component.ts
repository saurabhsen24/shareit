import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";
import { Post } from "src/app/shared/models/Post.model";

@Component({
  selector: "app-post",
  templateUrl: "./post.component.html",
  styleUrls: ["./post.component.css"],
})
export class PostComponent implements OnInit {
  post: Post = {
    postTitle: "Comedy Show",
    postDescription:
      "Bro this man is actual underrated standup gem... rewatched today and laughed as much as i did the first time and also as much as the audience😳",
    postId: 1,
    postUrl:
      "https://preview.redd.it/cdj44k37c3m91.png?width=640&crop=smart&auto=webp&s=02338df4afdcfc648619b5f248148533af492c96",
    voteCount: 0,
    userName: "Saurabh Sen",
  };

  constructor() {}

  ngOnInit() {}
}
