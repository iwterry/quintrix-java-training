package com.springboot.example.springwebservice.rest.service;

import com.springboot.example.springwebservice.rest.Isbn13ValidationRequest;

import java.util.function.Predicate;

public interface Isbn13ValidationService extends Predicate<Isbn13ValidationRequest> {
}
