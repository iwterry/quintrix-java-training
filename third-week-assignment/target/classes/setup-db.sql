CREATE DATABASE IF NOT EXISTS jdbc_practice;
USE jdbc_practice;

DROP TABLE IF EXISTS person;

CREATE TABLE person (
	id INT AUTO_INCREMENT,
	first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    age INT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO person(first_name, last_name, age)
VALUES ("John", "Doe", 23), ("Jane", "Smith", 40), ("Amy", "Johnson", 60);

SELECT * FROM person;