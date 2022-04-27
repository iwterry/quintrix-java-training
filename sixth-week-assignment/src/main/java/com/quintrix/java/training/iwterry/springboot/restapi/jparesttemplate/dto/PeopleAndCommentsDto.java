package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PeopleAndCommentsDto {
  @JsonProperty("people")
  private List<PersonDto> personDtos;
  @JsonProperty("comments")
  private List<CommentDto> commentDtos;

  public List<PersonDto> getPersonDtos() {
    return personDtos;
  }

  public void setPersonDtos(List<PersonDto> personDtos) {
    this.personDtos = personDtos;
  }

  public List<CommentDto> getCommentDtos() {
    return commentDtos;
  }

  public void setCommentDtos(List<CommentDto> commentDtos) {
    this.commentDtos = commentDtos;
  }
}
