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

  editorStyle = {
    height: "300px",
  };

  modules = {
    syntax: true,
    toolbar: [
      ["bold", "italic", "underline", "strike"],
      ["blockquote", "code-block"],
      [{ header: 1 }, { header: 2 }],
      [{ list: "ordered" }, { list: "bullet" }],
      [{ script: "sub" }, { script: "super" }],
      [{ indent: "-1" }, { indent: "+1" }],
      [{ direction: "rtl" }],
      [{ size: ["small", false, "large", "huge"] }],
      [{ header: [1, 2, 3, 4, 5, 6, false] }],
      [{ color: [] }, { background: [] }],
      [{ font: [] }],
      [{ align: [] }],
      ["clean"],
      ["link", "image"],
    ],
  };

  commentText = "";

  constructor() {}

  ngOnInit() {}
}
