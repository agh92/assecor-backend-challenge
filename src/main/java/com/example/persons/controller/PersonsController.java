package com.example.persons.controller;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.service.PersonsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonsController {

  private final PersonsService personsService;

  private final ModelMapper modelMapper;

  @GetMapping
  public List<PersonDto> getAllPersons() {
    List<Person> allPersons = personsService.findAll();
    return allPersons.stream().map(person -> modelMapper.map(person, PersonDto.class)).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerson(@RequestBody @Valid PersonDto personDto) {
    if (personDto.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id cannot be set by the client");
    }
    Person person = modelMapper.map(personDto, Person.class);
    this.personsService.createPerson(person);
  }

  @GetMapping("/{personID}")
  public PersonDto getPerson(@PathVariable Long personID) {
    Optional<Person> optionalPerson = personsService.findById(personID);

    if (optionalPerson.isPresent()) {
      return modelMapper.map(optionalPerson.get(), PersonDto.class);
    }

    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, String.format("Person with id %d not found", personID));
  }

  @GetMapping("/color/{color}")
  public List<PersonDto> getPersonsByColor(@PathVariable Color color) {
    List<Person> matchingPersons = personsService.findByColor(color);
    return matchingPersons.stream()
        .map(person -> modelMapper.map(person, PersonDto.class))
        .toList();
  }
}
