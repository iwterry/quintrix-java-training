package com.quintrix.java.training.iwterry.usingmongodb.controller;

import com.quintrix.java.training.iwterry.usingmongodb.httpservice.PersonHttpService;
import com.quintrix.java.training.iwterry.usingmongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


// The purpose of this class is to practice accessing a different service

@RestController
@RequestMapping("/api/v1/books-and-people")
public class BookAndPersonController {
  @Autowired
  private BookService bookService;
  @Autowired
  private PersonHttpService personHttpService;

  @GetMapping
  public ResponseEntity<?> getPeopleAndBooks() {
    Map<String, Object> objectMapToReturnToClient = new HashMap<>();

    objectMapToReturnToClient.put(
      "books",
      bookService.getAllBooks()
    );

    objectMapToReturnToClient.put(
      "people",
      personHttpService.getAllPeople()
    );

    return new ResponseEntity<>(objectMapToReturnToClient, HttpStatus.OK);
  }
}
