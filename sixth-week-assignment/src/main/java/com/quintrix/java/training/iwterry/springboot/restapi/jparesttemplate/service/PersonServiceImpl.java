package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.service;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.dto.PersonDto;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.exception.PersonNotFoundException;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.entity.Person;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
  private PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public PersonDto createPerson(PersonDto personDto) {
    Person person = convertFromDtoToModel(personDto);
    Person savedPerson = personRepository.save(person);
    return convertFromModelToDto(savedPerson);
  }

  @Override
  public List<PersonDto> getAllPeople() {
    Iterable<Person> personIterable = personRepository.findAll();
    List<PersonDto> personDtoList = new ArrayList<>();

    personIterable.forEach((person) -> {
      personDtoList.add(convertFromModelToDto(person));
    });

    return personDtoList;
  }

  @Override
  public List<PersonDto> getAllPeopleSortedByAgeAsc() {
    List<Person> personList = personRepository.getAllPeopleByOrderByAgeAsc();
    List<PersonDto> personDtoList = personList.stream()
      .map(this::convertFromModelToDto)
      .toList();
    return personDtoList;
  }

  @Override
  public PersonDto getPersonById(int id) {
    return convertFromModelToDto(getPersonByIdOrElseThrow(id));
  }

  @Override
  public PersonDto updatePerson(PersonDto personDto, int id) {
    Person personToBeUpdated = getPersonByIdOrElseThrow(id);
    personToBeUpdated.setAge(personDto.getAge());
    personToBeUpdated.setFirstName(personDto.getFirstName());
    personToBeUpdated.setLastName(personDto.getLastName());

    Person savedUpdatedPerson = personRepository.save(personToBeUpdated);
    return convertFromModelToDto(savedUpdatedPerson);
  }

  @Override
  public void deletePerson(int id) {
    Person personToBeDeleted = getPersonByIdOrElseThrow(id);
    personRepository.delete(personToBeDeleted);
  }


  private Person getPersonByIdOrElseThrow(int id) {
    return personRepository.findById(id)
      .orElseThrow(() -> new PersonNotFoundException(id));
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
