package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.service;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.PersonDto;

import java.util.List;

public interface PersonService {
  List<PersonDto> getAllPeople();
  List<PersonDto> getAllPeopleSortedByAgeAsc();
  PersonDto getPersonById(int id);
  PersonDto createPerson(PersonDto personDto);
  PersonDto updatePerson(PersonDto personDto, int id);
  void deletePerson(int id);
}
