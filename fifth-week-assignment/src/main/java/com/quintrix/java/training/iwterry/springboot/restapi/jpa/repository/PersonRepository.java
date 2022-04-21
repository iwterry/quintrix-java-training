package com.quintrix.java.training.iwterry.springboot.restapi.jpa.repository;

import com.quintrix.java.training.iwterry.springboot.restapi.jpa.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
  @Query("SELECT p FROM Person p ORDER BY p.age ASC") // <-- not needed, but this is how the query looks like
  List<Person> getAllPeopleByOrderByAgeAsc(); // <-- this method works fine without the annotation query
}
