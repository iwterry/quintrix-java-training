package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.controller;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.CommentDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
  @Autowired
  private CommentService commentService;

  @PostMapping
  public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
    CommentDto createdCommentDto = commentService.addComment(commentDto);
    return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<CommentDto>> getComments(@RequestParam(name = "name", defaultValue = "") String name) {
    List<CommentDto> commentDtos = commentService.getCommentsContainingName(name);
    return new ResponseEntity<>(commentDtos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") long id) {
    CommentDto commentDto = commentService.getCommentById(id);
    return new ResponseEntity<>(commentDto, HttpStatus.OK);
  }
}
