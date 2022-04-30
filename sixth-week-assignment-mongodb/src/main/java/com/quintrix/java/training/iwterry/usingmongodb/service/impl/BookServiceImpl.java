package com.quintrix.java.training.iwterry.usingmongodb.service.impl;

import com.quintrix.java.training.iwterry.usingmongodb.dto.BookDto;
import com.quintrix.java.training.iwterry.usingmongodb.entity.Book;
import com.quintrix.java.training.iwterry.usingmongodb.exception.BookNotFoundException;
import com.quintrix.java.training.iwterry.usingmongodb.repository.BookRepository;
import com.quintrix.java.training.iwterry.usingmongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {
  @Autowired
  private BookRepository bookRepository;

  @Override
  public List<BookDto> getAllBooks() {
    return bookRepository.findAll().stream()
      .map(this::convertFromEntityToDto)
      .toList();
  }

  @Override
  public BookDto getBookById(String id) {
    Book book = getBookByIdOrElseThrow(id);
    return convertFromEntityToDto(book);
  }

  @Override
  public BookDto createBook(BookDto bookDto) {
    Book book = convertFromDtoToEntity(bookDto);
    Book savedBook = bookRepository.save(book);
    return convertFromEntityToDto(savedBook);
  }

  @Override
  public BookDto updateBookById(BookDto bookDto, String id) {
    Book bookFromDb = getBookByIdOrElseThrow(id);

    bookFromDb.setTitle(bookDto.getTitle());
    bookFromDb.setNumOfPages(bookDto.getNumOfPages());

    bookFromDb = bookRepository.save(bookFromDb);

    return convertFromEntityToDto(bookFromDb);
  }

  @Override
  public void deleteBookById(String id) {
    Book book = getBookByIdOrElseThrow(id);
    bookRepository.delete(book);
  }

  private Book convertFromDtoToEntity(BookDto bookDto) {
    return new Book(
      null,
      bookDto.getTitle(),
      bookDto.getNumOfPages()
    );
  }

  private BookDto convertFromEntityToDto(Book book) {
    BookDto bookDto = new BookDto();

    bookDto.setId(book.getId());
    bookDto.setTitle(book.getTitle());
    bookDto.setNumOfPages(book.getNumOfPages());

    return bookDto;
  }

  private Book getBookByIdOrElseThrow(String id) {
    return bookRepository.findById(id)
      .orElseThrow(() -> new BookNotFoundException());
  }
}
