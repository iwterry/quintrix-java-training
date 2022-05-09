package com.springboot.example.springwebservice.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "the isbn13 value must consist of 13 digits")
public class InvalidIsbn13LengthException extends RuntimeException {
}
