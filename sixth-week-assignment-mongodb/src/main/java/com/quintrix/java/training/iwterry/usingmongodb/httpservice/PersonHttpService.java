package com.quintrix.java.training.iwterry.usingmongodb.httpservice;

import com.quintrix.java.training.iwterry.usingmongodb.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PersonHttpService {
  List<PersonDto> getAllPeople();
}
