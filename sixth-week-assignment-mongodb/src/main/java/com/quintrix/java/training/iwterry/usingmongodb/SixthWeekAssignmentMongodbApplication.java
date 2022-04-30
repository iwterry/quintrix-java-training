package com.quintrix.java.training.iwterry.usingmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SixthWeekAssignmentMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SixthWeekAssignmentMongodbApplication.class, args);
	}

}
