package com.quintrix.java.training.iwterry.usingmongodb.controller;

import com.quintrix.java.training.iwterry.usingmongodb.dto.BookDto;
import com.quintrix.java.training.iwterry.usingmongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
  @Autowired
  private BookService bookService;

  @PostMapping
  public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
    BookDto savedBookDto = bookService.createBook(bookDto);
    return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
  }

  @GetMapping
  public  ResponseEntity<List<BookDto>> getBooks() {
    List<BookDto> bookDtos = bookService.getAllBooks();
    return new ResponseEntity<>(bookDtos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> getBookById(@PathVariable("id") String id) {
    BookDto bookDto = bookService.getBookById(id);
    return new ResponseEntity<>(bookDto, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookDto> updateBookById(@Valid @RequestBody BookDto bookDto, @PathVariable("id") String id) {
    BookDto updatedBookDto = bookService.updateBookById(bookDto, id);
    return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBookById(@PathVariable("id") String id) {
    bookService.deleteBookById(id);
    return new ResponseEntity<>("The book with the given id has been successfully deleted.", HttpStatus.OK);
  }
}
