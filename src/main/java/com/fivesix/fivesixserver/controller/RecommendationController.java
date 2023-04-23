package com.fivesix.fivesixserver.controller;

import com.fivesix.fivesixserver.entity.Comment;
import com.fivesix.fivesixserver.entity.Movie;
import com.fivesix.fivesixserver.entity.RelatedDTO;
import com.fivesix.fivesixserver.mapper.CommentMapper;
import com.fivesix.fivesixserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationController {
    @Autowired
    CommentMapper commentMapper;

    public List<Movie> recommend(String username){
        List<Comment> comments = commentMapper.getAllCommentsByUsername(username);
        List<RelatedDTO> dto = new ArrayList<>();
        for(Comment comment : comments){
            dto.add(new RelatedDTO(comment.getUsername(), comment.getCinema_id(), comment.getRating()));
        }

    }
}
