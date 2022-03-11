package com.example.persons.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.utils.DummyPersonBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

@ExtendWith(MockitoExtension.class)
public class PersonsServiceImplTest {
  private PersonsServiceImpl personsService;

  @Mock private CrudRepository<Person, Long> repository;

  @BeforeEach
  void setUpService() {
    personsService = new PersonsServiceImpl(repository);
  }

  @Test
  void findAllReturnsAllPersonsInRepository() {
    Integer expectedNumberOfPersons = 5;
    given(repository.findAll()).willReturn(DummyPersonBuilder.buildPerson(expectedNumberOfPersons));

    List<Person> personList = personsService.findAll();

    assertEquals(expectedNumberOfPersons, personList.size());
  }

  @Test
  void findByIdReturnsTheCorrectPerson() {
    Person expectedPerson = DummyPersonBuilder.buildPerson();
    Optional<Person> emptyOptionalPerson = Optional.of(expectedPerson);
    given(repository.findById(expectedPerson.getId())).willReturn(emptyOptionalPerson);

    Person actualPerson = personsService.findById(expectedPerson.getId()).get();

    assertEquals(expectedPerson.getId(), actualPerson.getId());
  }

  @Test
  void findByColorReturnsEmptyListIfNoOneMatches() {
    int expectedMatches = 0;
    List<Person> nonMatchingPersons =
        Arrays.asList(
            DummyPersonBuilder.buildPerson(Color.BLUE),
            DummyPersonBuilder.buildPerson(Color.GREEN),
            DummyPersonBuilder.buildPerson(Color.RED));
    given(repository.findAll()).willReturn(nonMatchingPersons);

    List<Person> matchingPersons = personsService.findByColor(Color.TURQUOISE);

    assertEquals(expectedMatches, matchingPersons.size());
  }

  @Test
  void findByColorReturnsAllMatchingPersons() {
    int expectedMatches = 2;
    List<Person> nonMatchingPersons =
        Arrays.asList(
            DummyPersonBuilder.buildPerson(Color.BLUE),
            DummyPersonBuilder.buildPerson(Color.BLUE),
            DummyPersonBuilder.buildPerson(Color.RED));
    given(repository.findAll()).willReturn(nonMatchingPersons);

    List<Person> matchingPersons = personsService.findByColor(Color.BLUE);

    assertEquals(expectedMatches, matchingPersons.size());
  }
}
