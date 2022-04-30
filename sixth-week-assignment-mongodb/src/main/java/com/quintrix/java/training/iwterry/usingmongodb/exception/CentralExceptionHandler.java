package com.quintrix.java.training.iwterry.usingmongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CentralExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(CentralExceptionHandler.class);

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException bookNotFoundException, HttpServletRequest request) {
    log.error(request.getMethod()+ " " + request.getRequestURI(), bookNotFoundException);
    return new ResponseEntity<>("The given book with that id could not be found.", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Map<String, List<String>>>> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request) {
   log.error(request.getMethod() + " " + request.getRequestURI(), methodArgumentNotValidException);

    Map<String, List<String>> mapOfFieldErrorMsgs = methodArgumentNotValidException.getFieldErrors().stream()
      .collect(Collectors.groupingBy(
        FieldError::getField,
        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
      ));

    Map<String, Map<String, List<String>>> objToReturnToClient = new HashMap<>();
    objToReturnToClient.put("errors", mapOfFieldErrorMsgs);

    return new ResponseEntity<>(objToReturnToClient, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<String> handleHttpStatusCodeException(HttpStatusCodeException httpStatusCodeException) {
    log.error(httpStatusCodeException.getMessage(), httpStatusCodeException);
    return new ResponseEntity<>(httpStatusCodeException.getMessage(), httpStatusCodeException.getStatusCode());
  }
}
