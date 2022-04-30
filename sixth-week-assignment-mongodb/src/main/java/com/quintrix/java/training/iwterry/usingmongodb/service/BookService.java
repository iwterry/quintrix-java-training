package com.quintrix.java.training.iwterry.usingmongodb.service;

import com.quintrix.java.training.iwterry.usingmongodb.dto.BookDto;

import java.util.List;

public interface BookService {
  List<BookDto> getAllBooks();
  BookDto getBookById(String id);
  BookDto createBook(BookDto bookDto);
  BookDto updateBookById(BookDto bookDto, String id);
  void deleteBookById(String id);
}
