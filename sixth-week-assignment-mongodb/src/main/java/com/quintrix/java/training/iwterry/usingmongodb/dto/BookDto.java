package com.quintrix.java.training.iwterry.usingmongodb.dto;

import com.mongodb.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BookDto {
  private String id;
  @Size(min = 3, max = 100, message = "title must between 3 and 100 characters, inclusively")
  @NotBlank
  private String title;
  @Min(value = 1, message = "the number of pages must be at least 1.")
  private int numOfPages;

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
