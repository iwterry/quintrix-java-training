package com.springboot.example.springwebservice;

import com.springboot.example.springwebservice.consumingwebservice.Isbn13ValidatorClient;
import com.springboot.example.springwebservice.consumingwebservice.wsdl.IsValidISBN13Response;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringwebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringwebserviceApplication.class, args);
	}
}
