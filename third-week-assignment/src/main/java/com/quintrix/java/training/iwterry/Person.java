package com.quintrix.java.training.iwterry;

public class Person {
  private String firstName;
  private String lastName;
  private int age;
  private int id;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Person{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", age=" + age +
      ", id=" + id +
      '}';
  }
}
