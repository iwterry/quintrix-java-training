package com.springboot.example.springwebservice.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Isbn13ValidationResponse {
  private boolean isValid;

  public boolean isValid() {
    return isValid;
  }

  @JsonProperty("isValid")
  public void setValid(boolean valid) {
    isValid = valid;
  }
}
