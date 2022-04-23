package com.quintrix.java.training.iwterry.springboot.restapi.jpa.controller;

import com.quintrix.java.training.iwterry.springboot.restapi.jpa.dto.PersonDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jpa.exception.PersonNotFoundException;
import com.quintrix.java.training.iwterry.springboot.restapi.jpa.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {
  private PersonService personService;


  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping
  public List<PersonDto> getAllPeople(
    @RequestParam(name = "shouldSortByAgeAsc", defaultValue = "false") boolean shouldSortByAgeAsc
  ) {
    List<PersonDto> personDtoList = personService.getAllPeople();

    if (shouldSortByAgeAsc) {
      return personService.getAllPeopleSortedByAgeAsc();
    }

    return personDtoList;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPersonById(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(personService.getPersonById(id), HttpStatus.OK);
    } catch (PersonNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
    PersonDto savedPersonDto = personService.createPerson(personDto);
    return new ResponseEntity<>(savedPersonDto, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto, @PathVariable("id") int id) {
    try {
      PersonDto updatedPersonDto = personService.updatePerson(personDto, id);
      return new ResponseEntity<>(updatedPersonDto, HttpStatus.OK);
    } catch (PersonNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePerson(@PathVariable("id") int id) {
    try {
      personService.deletePerson(id);
      return new ResponseEntity<>("Deletion was successful.", HttpStatus.OK);
    } catch (PersonNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
