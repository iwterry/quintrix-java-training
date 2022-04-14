package com.quintrix.java.training.iwterry.springboot.restapi.service;

import com.quintrix.java.training.iwterry.springboot.restapi.dto.PersonDto;

import java.util.List;

public interface PersonService {
  List<PersonDto> getAllPeople();
  List<PersonDto> getAllPeopleSortedByAgeAsc();
  PersonDto getPersonById(int id);
  PersonDto createPerson(PersonDto personDto);
}
