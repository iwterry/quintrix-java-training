package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.CommentDto;

import java.util.List;

public interface CommentService {
  CommentDto addComment(CommentDto commentDto);
  CommentDto getCommentById(long id);
  List<CommentDto> getCommentsContainingName(String name);
}
