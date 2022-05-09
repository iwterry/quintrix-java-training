package com.springboot.example.springwebservice.consumingwebservice;

import com.springboot.example.springwebservice.consumingwebservice.wsdl.IsValidISBN13;
import com.springboot.example.springwebservice.consumingwebservice.wsdl.IsValidISBN13Response;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class Isbn13ValidatorClient extends WebServiceGatewaySupport {
  public IsValidISBN13Response getIsIsbn13Valid(String isbn13) {
    IsValidISBN13 request = new IsValidISBN13();
    request.setSISBN(isbn13);

    IsValidISBN13Response response = (IsValidISBN13Response) getWebServiceTemplate().marshalSendAndReceive(request);
    return response;
  }
}
