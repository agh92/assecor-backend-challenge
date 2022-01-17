package com.example.persons.service;

import com.example.persons.model.Color;
import com.example.persons.model.Person;

import java.util.List;
import java.util.Optional;

/** Abstracts the communication with the repository that provides {@link Person} */
public interface PersonsService {

  /** @return all persons in the repository - might be empty if there are any */
  List<Person> findAll();

  /**
   * @param id of the person to find
   * @return optional holding the person found - optional will be empty if there is no person with
   *     the given id
   */
  Optional<Person> findById(Long id);

  /**
   * @param color to be used to filter the persons
   * @return all persons in the repository that have the given color
   */
  List<Person> findByColor(Color color);

  /**
   * creates a new person in the repository
   *
   * @param person person to be saved in the repository
   */
  void createPerson(Person person);
}
