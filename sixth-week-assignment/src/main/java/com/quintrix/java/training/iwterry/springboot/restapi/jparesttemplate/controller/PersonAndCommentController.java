package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.controller;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.CommentDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.PeopleAndCommentsDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.PersonDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice.CommentService;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people-and-comments")
public class PersonAndCommentController {
  @Autowired
  private PersonService personService;
  @Autowired
  private CommentService commentService;


  @GetMapping
  public ResponseEntity<?> getPeopleAndComments() {
    List<PersonDto> personDtos = personService.getAllPeople();
    List<CommentDto> commentDtos = null;

    try {
      commentDtos = commentService.getCommentsContainingName("");
      PeopleAndCommentsDto peopleAndCommentsDto = new PeopleAndCommentsDto();
      peopleAndCommentsDto.setCommentDtos(commentDtos);
      peopleAndCommentsDto.setPersonDtos(personDtos);
      return new ResponseEntity<>(peopleAndCommentsDto, HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

  }

}
