package com.example.persons.controller;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.service.PersonsService;
import com.example.persons.utils.PersonModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonsController {

    private final PersonsService personsService;

    @GetMapping
    public List<PersonDto> getAllPersons() {
        List<Person> allPersons = personsService.findAll();
        return allPersons.stream().map(PersonModelMapper::map).toList();
    }

    @GetMapping("/{personID}")
    public PersonDto getPerson(@PathVariable Long personID) {
        Person person = personsService.findById(personID);
        return PersonModelMapper.map(person);
    }

    @GetMapping("/color/{color}")
    public List<PersonDto> getMatchingPersons(@PathVariable Color color) {
        List<Person> matchingPersons = personsService.findByColor(color);
        return matchingPersons.stream().map(PersonModelMapper::map).toList();
    }


}
