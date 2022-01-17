package com.example.persons.service;

import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.personfile.PersonFileLoader;
import com.example.persons.personfile.PersonParser;
import com.example.persons.utils.DummyPersonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PersonsServiceImplTest {
  PersonsServiceImpl personsService;

  @Mock CrudRepository<Person, Long> repository;
  @Mock PersonFileLoader personFileLoader;
  @Mock PersonParser personParser;

  @BeforeEach
  void setUpService() {
    personsService = new PersonsServiceImpl(repository, personFileLoader, personParser);
  }

  @Test
  void getAllPersonsReturnsAllPersonsInRepository() {
    Integer expectedNumberOfPersons = 5;
    given(repository.findAll()).willReturn(DummyPersonBuilder.buildPerson(expectedNumberOfPersons));

    List<Person> personList = personsService.findAll();

    assertEquals(expectedNumberOfPersons, personList.size());
  }

  @Test
  void getPersonReturnsTheCorrectPerson() {
    Person expectedPerson = DummyPersonBuilder.buildPerson();
    Optional<Person> emptyOptionalPerson = Optional.of(expectedPerson);
    given(repository.findById(expectedPerson.getId())).willReturn(emptyOptionalPerson);

    Person actualPerson = personsService.findById(expectedPerson.getId()).get();

    assertEquals(expectedPerson.getId(), actualPerson.getId());
  }

  @Test
  void getMatchingPersonsReturnsEmptyListIfNoOneMatches() {
    List<Person> nonMatchingPersons =
        Arrays.asList(
            DummyPersonBuilder.buildPerson(Color.BLUE),
            DummyPersonBuilder.buildPerson(Color.GREEN),
            DummyPersonBuilder.buildPerson(Color.RED));
    given(repository.findAll()).willReturn(nonMatchingPersons);

    List<Person> matchingPersons = personsService.findByColor(Color.TURQUOISE);

    assertEquals(0, matchingPersons.size());
  }

  @Test
  void getMatchingPersonsReturnsAllMatchingPersons() {
    List<Person> nonMatchingPersons =
        Arrays.asList(
            DummyPersonBuilder.buildPerson(Color.BLUE),
            DummyPersonBuilder.buildPerson(Color.BLUE),
            DummyPersonBuilder.buildPerson(Color.RED));
    given(repository.findAll()).willReturn(nonMatchingPersons);

    List<Person> matchingPersons = personsService.findByColor(Color.BLUE);

    assertEquals(2, matchingPersons.size());
  }
}
