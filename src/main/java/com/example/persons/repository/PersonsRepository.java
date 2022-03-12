package com.example.persons.repository;

import com.example.persons.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonsRepository extends CrudRepository<Person, Long> {}
