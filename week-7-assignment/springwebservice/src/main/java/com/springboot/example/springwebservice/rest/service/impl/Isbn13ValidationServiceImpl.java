package com.springboot.example.springwebservice.rest.service.impl;

import com.springboot.example.springwebservice.consumingwebservice.Isbn13ValidatorClient;
import com.springboot.example.springwebservice.rest.Isbn13ValidationRequest;
import com.springboot.example.springwebservice.rest.exception.InvalidIsbn13LengthException;
import com.springboot.example.springwebservice.rest.service.Isbn13ValidationService;
import org.springframework.stereotype.Service;

@Service
public class Isbn13ValidationServiceImpl implements Isbn13ValidationService {
  private final Isbn13ValidatorClient client;

  public Isbn13ValidationServiceImpl(Isbn13ValidatorClient client) {
    this.client = client;
  }

  @Override
  // assume request is not null.
  // assume request.getIsbn13Value() is not null and consists of only digits, hyphens, and space characters.
  public boolean test(Isbn13ValidationRequest request) {
    String str = request.getIsbn13Value().replaceAll(" |-", "");

    if(str.length() != 13) {
      throw new InvalidIsbn13LengthException();
    }

    return client.getIsIsbn13Valid(str).isIsValidISBN13Result();
  }
}
