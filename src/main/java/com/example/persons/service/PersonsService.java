package com.example.persons.service;

import com.example.persons.exception.PersonNotFoundException;
import com.example.persons.model.Color;
import com.example.persons.model.Person;

import java.util.List;

public interface PersonsService {

    List<Person> findAll();

    Person findById(Long id) throws PersonNotFoundException;

    List<Person> findByColor(Color color);
}
