package com.quintrix.java.training.iwterry.usingmongodb.httpservice.impl;

import com.quintrix.java.training.iwterry.usingmongodb.dto.PersonDto;
import com.quintrix.java.training.iwterry.usingmongodb.httpservice.PersonHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PersonHttpServiceImpl implements PersonHttpService {
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public List<PersonDto> getAllPeople() {
    ResponseEntity<List<PersonDto>> responseEntity =  restTemplate.exchange(
      "http://localhost:8080/api/v1/people",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<PersonDto>>() {}
    );

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      return responseEntity.getBody();
    }

    return null;
  }
}
