package com.quintrix.java.training.iwterry.usingmongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {
  @Id
  private String id;
  private String title;
  private int numOfPages;

  public Book(String id, String title, int numOfPages) {
    this.id = id;
    this.title = title;
    this.numOfPages = numOfPages;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getNumOfPages() {
    return numOfPages;
  }

  public void setNumOfPages(int numOfPages) {
    this.numOfPages = numOfPages;
  }
}
