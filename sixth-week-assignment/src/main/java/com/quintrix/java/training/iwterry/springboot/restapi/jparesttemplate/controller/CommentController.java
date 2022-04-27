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
  public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
    try {
      CommentDto createdCommentDto = commentService.addComment(commentDto);
      return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    } catch (HttpStatusCodeException e) {
      return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
  }

  @GetMapping
  public ResponseEntity<?> getComments(@RequestParam(name = "name", defaultValue = "") String name) {
    try {
      List<CommentDto> commentDtos = commentService.getCommentsContainingName(name);
      return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCommentById(@PathVariable(name = "id") long id) {
    try {
      CommentDto commentDto = commentService.getCommentById(id);
      return new ResponseEntity<>(commentDto, HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
  }
}
