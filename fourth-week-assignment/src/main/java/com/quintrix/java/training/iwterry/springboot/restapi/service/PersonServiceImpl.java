package com.quintrix.java.training.iwterry.springboot.restapi.service;

import com.quintrix.java.training.iwterry.springboot.restapi.dto.PersonDto;
import com.quintrix.java.training.iwterry.springboot.restapi.model.Person;
import com.quintrix.java.training.iwterry.springboot.restapi.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
  private PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public List<PersonDto> getAllPeople() {
    List<Person> personList = personRepository.getAllPeople();
    List<PersonDto> personDtoList = personList.stream()
      .map(this::convertFromModelToDto)
      .toList();
    return personDtoList;
  }

  @Override
  public List<PersonDto> getAllPeopleSortedByAgeAsc() {
    List<Person> personList = personRepository.getAllPeopleSortedByAgeAsc();
    List<PersonDto> personDtoList = personList.stream()
      .map(this::convertFromModelToDto)
      .toList();
    return personDtoList;
  }

  @Override
  public PersonDto getPersonById(int id) {
    Person person = personRepository.getPersonById(id);
    return convertFromModelToDto(person);
  }

  @Override
  public PersonDto createPerson(PersonDto personDto) {
    Person person = convertFromDtoToModel(personDto);
    Person savedPerson = personRepository.save(person);
    return convertFromModelToDto(savedPerson);
  }

  private PersonDto convertFromModelToDto(Person person) {
    PersonDto personDto = new PersonDto();

    personDto.setId(person.getId());
    personDto.setFirstName(person.getFirstName());
    personDto.setLastName(person.getLastName());
    personDto.setAge(person.getAge());

    return personDto;
  }

  private Person convertFromDtoToModel(PersonDto personDto) {
    Person person = new Person();

    person.setFirstName(personDto.getFirstName());
    person.setLastName(personDto.getLastName());
    person.setAge(personDto.getAge());

    return person;
  }
}
