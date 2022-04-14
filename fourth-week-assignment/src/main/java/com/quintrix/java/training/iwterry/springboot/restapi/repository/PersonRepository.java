package com.quintrix.java.training.iwterry.springboot.restapi.repository;

import com.quintrix.java.training.iwterry.springboot.restapi.model.Person;

import java.util.List;

public interface PersonRepository {
  List<Person> getAllPeople();
  List<Person> getAllPeopleSortedByAgeAsc();
  Person getPersonById(int id);
  Person save(Person person);
}
