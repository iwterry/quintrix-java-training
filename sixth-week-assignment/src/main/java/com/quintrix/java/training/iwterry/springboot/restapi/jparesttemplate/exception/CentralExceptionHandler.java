package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
public class CentralExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(CentralExceptionHandler.class);

  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<String> handlePersonNotFoundException(PersonNotFoundException personNotFoundException) {
    logError(personNotFoundException);
    return new ResponseEntity<>(personNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<String> handleHttpStatusCodeException(HttpStatusCodeException httpStatusCodeException) {
    logError(httpStatusCodeException);
    return new ResponseEntity<>(httpStatusCodeException.getMessage(), httpStatusCodeException.getStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception exception) {
    logError(exception);
    return new ResponseEntity<>("Sorry, something occurred on our end. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private void logError(Exception exception) {
    log.error(exception.getClass().getName(), exception);
  }
}
