package com.example.persons.service;

import com.example.persons.domain.Color;
import com.example.persons.domain.Person;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PersonsServiceImpl implements PersonsService {

  private final CrudRepository<Person, Long> personRepository;

  public List<Person> findAll() {
    var persons = personRepository.findAll();
    return StreamSupport.stream(persons.spliterator(), false).collect(Collectors.toList());
  }

  public Optional<Person> findById(Long id) {
    return personRepository.findById(id);
  }

  public List<Person> findByColor(Color color) {
    var persons = personRepository.findAll();

    return StreamSupport.stream(persons.spliterator(), false)
        .filter(person -> person.getColor() == color)
        .collect(Collectors.toList());
  }

  public void createPerson(Person person) {
    this.personRepository.save(person);
  }
}
