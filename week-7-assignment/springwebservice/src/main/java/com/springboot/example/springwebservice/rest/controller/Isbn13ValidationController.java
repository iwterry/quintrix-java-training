package com.springboot.example.springwebservice.rest.controller;

import com.springboot.example.springwebservice.rest.Isbn13ValidationRequest;
import com.springboot.example.springwebservice.rest.Isbn13ValidationResponse;
import com.springboot.example.springwebservice.rest.service.Isbn13ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/isbn13-validation")
public class Isbn13ValidationController {
  private final Isbn13ValidationService isbn13ValidationService;

  public Isbn13ValidationController(Isbn13ValidationService isbn13ValidationService) {
    this.isbn13ValidationService = isbn13ValidationService;
  }

  @PostMapping
  public ResponseEntity<Isbn13ValidationResponse> validateIsbn(@Valid @RequestBody Isbn13ValidationRequest request) {
    Isbn13ValidationResponse response = new Isbn13ValidationResponse();
    response.setValid(isbn13ValidationService.test(request));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
