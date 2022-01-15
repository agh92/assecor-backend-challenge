package com.example.persons.service;

import com.example.persons.model.Color;
import com.example.persons.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonsService {

    List<Person> findAll();

    Optional<Person> findById(Long id);

    List<Person> findByColor(Color color);

    void createPerson(Person person);
}
