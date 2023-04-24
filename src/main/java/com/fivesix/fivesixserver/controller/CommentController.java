package com.fivesix.fivesixserver.controller;

import com.fivesix.fivesixserver.entity.Comment;
import com.fivesix.fivesixserver.mapper.CommentMapper;
import com.fivesix.fivesixserver.result.Result;
import com.fivesix.fivesixserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    CommentMapper commentMapper;

    @GetMapping("api/user/comment/add")
    public Result addComment(@RequestParam(name = "commentId") String commentId, @RequestParam(name="username") String username, @RequestParam(name="comment") String comment, @RequestParam(name="comment_zh") String comment_zh) throws IOException, ClassNotFoundException {
        double rating = commentService.getScore(comment);
        String mood = null;
        // english
        if(rating == 0){
            mood = "neutral";
        } else if(rating > 0){
            mood = "positive";
        } else {
            mood = "negative";
        }
        commentMapper.addComment(commentId, username, comment, comment_zh, mood, rating);
        return new Result(200, "success");
    }
    @GetMapping("api/user/comment/getall")
    public List<Comment> getAllcomments(){
        return commentMapper.getAllComments();
    }

}
