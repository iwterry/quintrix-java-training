package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.repository;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
  @Query("SELECT p FROM Person p ORDER BY p.age ASC") // <-- not needed, but this is how the query looks like
  List<Person> getAllPeopleByOrderByAgeAsc(); // <-- this method works fine without the annotation query
}
