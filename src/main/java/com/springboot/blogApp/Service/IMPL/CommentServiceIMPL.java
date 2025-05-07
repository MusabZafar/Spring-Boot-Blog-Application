package com.springboot.blogApp.Service.IMPL;

import DTO_payLoad.CommentDTO;
import com.springboot.blogApp.Entity.Comment;
import com.springboot.blogApp.Entity.Post;
import com.springboot.blogApp.Exception.BlogAPIException;
import com.springboot.blogApp.Exception.ResourceNotFound;
import com.springboot.blogApp.Repository.CommentRepository;
import com.springboot.blogApp.Repository.PostRepository;
import com.springboot.blogApp.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceIMPL implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;



    public CommentServiceIMPL(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper ) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mappingComment(commentDTO);
        //Retrive post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ( )-> new ResourceNotFound("Post", "id", postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity to db
        Comment newComment=commentRepository.save(comment);
        return mappingCommentDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postid) {
        //Retrieve comments by postId
        List<Comment> comments=commentRepository.findByPostId(postid);
        return comments.stream().map(comment -> mappingCommentDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postid,long commentId) {
        Post post=postRepository.findById(postid).orElseThrow(()->new ResourceNotFound("Post","id", postid));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("Post","id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return mappingCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postid, long commentId, CommentDTO commentRequest) {
        Post post=postRepository.findById(postid).orElseThrow(()->new ResourceNotFound("Post","id", postid));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("Post","id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setBody(commentRequest.getBody());
        comment.setPost(post);
        Comment updatedComment=commentRepository.save(comment);
        return mappingCommentDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postid, long commentId) {
        Post post=postRepository.findById(postid).orElseThrow(()->new ResourceNotFound("Post","id", postid));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("Post","id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }

    //    Convert Entity to DTO
    private CommentDTO mappingCommentDTO(Comment comment) {
        CommentDTO commentDTO=modelMapper.map(comment, CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }
//  convert dto to entity
    private Comment mappingComment(CommentDTO commentDTO) {
        Comment comment=modelMapper.map(commentDTO, Comment.class);
//        Comment comment=new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
