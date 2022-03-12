package com.example.persons.controller;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.service.PersonsService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonsController {

  private final PersonsService personsService;

  private final ModelMapper modelMapper;

  @GetMapping
  public List<PersonDto> getAllPersons() {
    var allPersons = personsService.findAll();
    return allPersons.stream().map(person -> modelMapper.map(person, PersonDto.class)).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerson(@RequestBody @Valid PersonDto personDto) {
    var person = modelMapper.map(personDto, Person.class);
    this.personsService.createPerson(person);
  }

  @GetMapping("/{personID}")
  public PersonDto getPerson(@PathVariable Long personID) {
    var optionalPerson = personsService.findById(personID);

    if (optionalPerson.isPresent()) {
      return modelMapper.map(optionalPerson.get(), PersonDto.class);
    }

    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, String.format("Person with id %d not found", personID));
  }

  @GetMapping("/color/{color}")
  public List<PersonDto> getPersonsByColor(@PathVariable Color color) {
    var matchingPersons = personsService.findByColor(color);
    return matchingPersons.stream()
        .map(person -> modelMapper.map(person, PersonDto.class))
        .toList();
  }
}
