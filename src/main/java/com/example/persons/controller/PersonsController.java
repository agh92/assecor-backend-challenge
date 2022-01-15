package com.example.persons.controller;

import com.example.persons.converter.StringToColorDtoConverter;
import com.example.persons.dto.ColorDto;
import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.service.PersonsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void createPerson(@RequestBody PersonDto personDto) {
        Person person = modelMapper.map(personDto, Person.class);
        this.personsService.createPerson(person);
    }

    @GetMapping("/{personID}")
    public PersonDto getPerson(@PathVariable Long personID) {
        Person person = personsService.findById(personID);
        return modelMapper.map(person, PersonDto.class);
    }

    @GetMapping("/color/{coloDto}")
    public List<PersonDto> getPersonsByColor(@PathVariable ColorDto coloDto) {
        Color color = modelMapper.map(coloDto, Color.class);
        List<Person> matchingPersons = personsService.findByColor(color);
        return matchingPersons.stream().map(person -> modelMapper.map(person, PersonDto.class)).toList();
    }
}
