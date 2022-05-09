package com.springboot.example.springwebservice.rest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Isbn13ValidationRequest {
  @NotBlank
  @Size(min = 13)
  @Pattern(regexp = "^[0-9\\- ]+$")
  private String isbn13Value;

  public String getIsbn13Value() {
    return isbn13Value;
  }

  public void setIsbn13Value(String isbn13Value) {
    this.isbn13Value = isbn13Value;
  }
}
