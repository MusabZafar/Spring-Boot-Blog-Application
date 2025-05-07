package com.springboot.blogApp.Service;

import DTO_payLoad.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId,CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(long postid);
    CommentDTO getCommentById(long postid,long commentId);
    CommentDTO updateComment(long postid,long commentId,CommentDTO commentRequest);
    void deleteComment(long postid,long commentId);
}
