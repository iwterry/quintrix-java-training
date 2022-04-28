package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice.impl;


import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.CommentDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CommentServiceImpl implements CommentService {
  private final RestTemplate restTemplate;
  private final String authToken;
  private final Logger log;
  private final String url = "https://gorest.co.in/public/v2/comments";

  public CommentServiceImpl(RestTemplate restTemplate, String authToken) {
    this.restTemplate = restTemplate;
    this.authToken = authToken;
    log = LoggerFactory.getLogger(this.getClass());
  }

  @Override
  public CommentDto addComment(CommentDto commentDto) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(this.authToken);

    // just doing some logging for practice
    log.info("Making a POST request to {}", this.url);

    ResponseEntity<CommentDto> responseEntity = this.restTemplate.postForEntity(
      this.url,
      new HttpEntity<>(commentDto, httpHeaders),
      CommentDto.class
    );

    if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
      return responseEntity.getBody();
    }

    return null;
  }

  @Override
  public CommentDto getCommentById(long id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(this.authToken);

    ResponseEntity<CommentDto> responseEntity = this.restTemplate.exchange(
      this.url + "/{id}",
      HttpMethod.GET,
      new HttpEntity<>(httpHeaders),
      CommentDto.class,
      id
    );

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      return responseEntity.getBody();
    }

    return null;
  }

  @Override
  public List<CommentDto> getCommentsContainingName(String name) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(this.authToken);

    ResponseEntity<List<CommentDto>> responseEntity = this.restTemplate.exchange(
      this.url + "?name={name}",
      HttpMethod.GET,
      new HttpEntity<>(httpHeaders),
      new ParameterizedTypeReference<>() {},
      name
    );

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      return responseEntity.getBody();
    }

    return null;
  }
}
