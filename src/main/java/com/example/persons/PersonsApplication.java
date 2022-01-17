package com.example.persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class PersonsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PersonsApplication.class, args);
  }
}
