package com.example.persons.controller;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @GetMapping
    public PersonDto[] getAllPersons() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented");
    }

    @GetMapping("/{personID}")
    public PersonDto getPerson(@PathVariable Integer personID) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented");
    }

    @GetMapping("/color/{color}")
    public PersonDto[] getPersonsWithSameFavoriteColor(@PathVariable Color color) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented");
    }


}
