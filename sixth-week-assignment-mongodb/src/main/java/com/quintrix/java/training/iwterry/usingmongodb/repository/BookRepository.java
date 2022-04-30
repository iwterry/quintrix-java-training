package com.quintrix.java.training.iwterry.usingmongodb.repository;

import com.quintrix.java.training.iwterry.usingmongodb.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
