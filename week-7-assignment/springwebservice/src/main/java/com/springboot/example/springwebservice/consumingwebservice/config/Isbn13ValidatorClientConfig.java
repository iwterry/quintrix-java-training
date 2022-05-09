package com.springboot.example.springwebservice.consumingwebservice.config;

import com.springboot.example.springwebservice.consumingwebservice.Isbn13ValidatorClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Isbn13ValidatorClientConfig {
  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.springboot.example.springwebservice.consumingwebservice.wsdl");
    return marshaller;
  }

  @Bean
  public Isbn13ValidatorClient isbn13ValidatorClient(Jaxb2Marshaller marshaller) {
    Isbn13ValidatorClient client = new Isbn13ValidatorClient();
    client.setDefaultUri("http://webservices.daehosting.com/services/isbnservice.wso?WSDL");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
