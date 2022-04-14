package com.quintrix.java.training.iwterry.springboot.restapi.repository;

import com.quintrix.java.training.iwterry.springboot.restapi.exception.PersonNotFoundException;
import com.quintrix.java.training.iwterry.springboot.restapi.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
  private List<Person> personList;
  private static int counter;

  {
    personList = new ArrayList<>(
      List.of(
        new Person(1, "John", "Doe", 23),
        new Person(2, "Jane", "Adams", 12),
        new Person(3, "Mary", "Williams", 35),
        new Person(4, "Greg", "Mane", 30)
      )
    );

    counter = personList.size();
  }

  @Override
  public List<Person> getAllPeople() {
    return personList.stream()
      .map(this::getCopyOfPerson)
      .toList();
  }

  @Override
  public List<Person> getAllPeopleSortedByAgeAsc() {
    return personList.stream()
      .sorted(Comparator.comparingInt(Person::getAge))
      .map(this::getCopyOfPerson)
      .toList();
  }

  @Override
  public Person getPersonById(int id) {
    return getCopyOfPerson(getActualPersonById(id));
  }

  @Override
  public Person save(Person person) {
    validatePerson(person);

    if (person.getId() >= 1) {
      Person personFromTheList = getActualPersonById(person.getId());
      personFromTheList.setAge(person.getAge());
      personFromTheList.setFirstName(person.getFirstName());
      personFromTheList.setLastName(person.getLastName());

      return getCopyOfPerson(personFromTheList);
    }

    Person copyOfPerson = getCopyOfPerson(person);
    PersonRepositoryImpl.counter += 1;
    copyOfPerson.setId(PersonRepositoryImpl.counter);
    personList.add(copyOfPerson);

    return copyOfPerson;
  }

  // copying the data is just a way to protect the data of this class
  private Person getCopyOfPerson(Person person) {
    return new Person(
      person.getId(),
      person.getFirstName(),
      person.getLastName(),
      person.getAge()
    );
  }

  private void validatePerson(Person person) {
    try {
      Objects.requireNonNull(person);
      Objects.requireNonNull(person.getFirstName());
      Objects.requireNonNull(person.getLastName());

      if(
        person.getFirstName().isBlank() ||
        person.getLastName().isBlank() ||
        person.getAge() < 0
      ) throw new IllegalArgumentException();

    } catch (NullPointerException | IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid data was given for person.");
    }
  }

  private Person getActualPersonById(int id) {
    return personList.stream()
      .filter(person -> person.getId() == id)
      .findAny()
      .orElseThrow(() -> new PersonNotFoundException(id));
  }
}
