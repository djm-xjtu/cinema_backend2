package com.fivesix.fivesixserver.mapper;

import com.fivesix.fivesixserver.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    // add comment in comment table
    @Select("INSERT INTO `comment` (`cinema_id`,`username`, `comment`, `mood`, `rating`) VALUES (#{cinema_id},#{username}, #{content}, #{mood}, #{rating})")
    void addComment(@Param("cinema_id") String cinema_id, @Param("username") String username, @Param("comment") String comment, @Param("mood") String mood, @Param("rating") double rating);

    // get all comments
    @Select("SELECT * FROM `comment`")
    List<Comment> getAllComments();

    // get all comments by username
    @Select("SELECT * FROM `comment` WHERE `username` = #{username}")
    List<Comment> getAllCommentsByUsername(@Param("username") String username);

}
