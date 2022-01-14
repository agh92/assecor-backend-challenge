package com.example.persons;

import com.example.persons.configuration.BeansConfiguration;
import com.example.persons.configuration.WebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@EnableAutoConfiguration
@ComponentScan
@Import({BeansConfiguration.class, WebMvcConfiguration.class})
public class PersonsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonsApplication.class, args);
    }
}
