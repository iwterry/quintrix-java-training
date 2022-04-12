package com.quintrix.java.training.iwterry;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String url = "jdbc:mysql://localhost:3306/jdbc_practice";
    // the username and password can be changed as needed
    private static final String username = "root";
    private static final String password = "password";

    public static void main(String[] args) {
        // Please execute the SQL file in the src/main/resources directory before each execution of this program.

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("--------------- Assignment using JDBC ---------------------");
        String allPeopleJsonStr = getAllPeople();
        System.out.println(allPeopleJsonStr);

        String updatedPersonJsonStr = "{" +
          "\"firstName\" : \"Jen\", " +
          "\"lastName\" : \"Smith\", " +
          "\"age\" : 40" +
          "}";
        int idOfPersonToUpdate = 2;
        updatePersonWithId(updatedPersonJsonStr, idOfPersonToUpdate);
        System.out.println(getAllPeople());

        String newPersonJsonStr = "{" +
          "\"firstName\" : \"James\", " +
          "\"lastName\" : \"French\", " +
          "\"age\" : 23" +
          "}";
        addPerson(newPersonJsonStr);
        System.out.println(getAllPeople());

        System.out.println();

        System.out.println("--------------Assignment using Java Streams API --------------");
        List<Person> allPeople;
        try {
            allPeople = Arrays.asList(objectMapper.readValue(getAllPeople(), Person[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        if (allPeople.isEmpty()) return; // there is no data to use from the database to use for the Streams API
        printInfoAboutPeople(allPeople);
    }

    private static void printInfoAboutPeople(List<Person> allPeople) {
        long numOfPeopleWhoseFirstNameComesBeforeE = allPeople.stream()
          .map(Person::getFirstName)
          .map(String::toLowerCase)
          .map((firstName) -> firstName.charAt(0))
          .filter((firstLetterOfFirstName) -> firstLetterOfFirstName < 'e')
          .count();

        System.out.println("Let's find out some information about the people.");

        System.out.println("- number of people whose first letter of last names come before e: " + numOfPeopleWhoseFirstNameComesBeforeE);

        double avgAgeOfAllPeople = allPeople.stream()
          .mapToInt(Person::getAge)
          .average()
          .getAsDouble();

        System.out.println("- average age of all people: " + avgAgeOfAllPeople);


        Person oldestPerson = allPeople.stream()
          .max(Comparator.comparingInt(Person::getAge))
          .get();

        System.out.println("- a person with the oldest age is --> " + oldestPerson);

        List<Person> twoYoungestPeople = allPeople.stream()
          .sorted(Comparator.comparingInt(Person::getAge))
          .limit(2)
          .toList();

        System.out.println("- two of the people with the youngest ages are -->" + twoYoungestPeople);
    }

    private static void addPerson(String personJsonString) {
        try (
          Connection connection = DriverManager.getConnection(url, username, password);
          PreparedStatement statement = connection.prepareStatement("INSERT INTO person(first_name, last_name, age) VALUES (?, ?, ?)")
        ) {
            Person person = objectMapper.readValue(personJsonString, Person.class);

            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getAge());

            int result = statement.executeUpdate();
            System.out.println("Num of rows inserted: " + result);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static void updatePersonWithId(String personJsonString, int id) {
        try (
          Connection connection = DriverManager.getConnection(url, username, password);
          PreparedStatement statement = connection.prepareStatement("UPDATE person SET first_name=?, last_name=?, age=? WHERE id=?")
        ) {
            Person person = objectMapper.readValue(personJsonString, Person.class);

            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getAge());
            statement.setInt(4, id);

            int result = statement.executeUpdate();
            System.out.println("Num of rows updated: " + result);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static String getAllPeople() {
        try (
          Connection connection = DriverManager.getConnection(url, username, password);
          Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery("SELECT * FROM person")
        ) {
            List<Person> personList = new ArrayList<>();
            Person person;

            while (resultSet.next()) {
                person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setAge(resultSet.getInt("age"));

                personList.add(person);
            }

            return objectMapper.writeValueAsString(personList);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return ""; // choosing to just return an empty string if error occurs
    }
}