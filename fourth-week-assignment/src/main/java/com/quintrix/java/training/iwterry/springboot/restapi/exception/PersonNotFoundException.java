package com.quintrix.java.training.iwterry.springboot.restapi.exception;

public class PersonNotFoundException extends RuntimeException {
  private int personId;

  public PersonNotFoundException(int personId) {
    super(String.format("The person with id=%s could not be found", personId));
    this.personId = personId;
  }

  public int getPersonId() {
    return personId;
  }
}
