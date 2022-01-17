package com.example.persons.configuration;


import com.example.persons.configuration.BeansConfiguration;
import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {BeansConfiguration.class})
public class ModelMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void mapsPersonToPersonDto() {
        String expectedColor = "blue";
        Integer expectedZipCode = 12345;
        String expectedCity = "somewhere";

        Person person = Person.builder()
                .id(1L)
                .name("john")
                .lastName("doe")
                .address(expectedZipCode + " " + expectedCity)
                .color(Color.BLUE)
                .build();

        PersonDto personDto = modelMapper.map(person, PersonDto.class);

        assertEquals(person.getId(), personDto.getId());
        assertEquals(person.getName(), personDto.getName());
        assertEquals(person.getLastName(), personDto.getLastName());
        assertEquals(expectedZipCode, personDto.getZipCode());
        assertEquals(expectedCity, personDto.getCity());
        assertEquals(expectedColor, personDto.getColor());
    }

    @Test
    void mapsPersonDtoToPerson() {
        Color expectedColor = Color.BLUE;
        String expectedAddress = "12345 somewhere";

        PersonDto personDto = PersonDto.builder()
                .id(1L)
                .name("john")
                .lastName("doe")
                .zipCode(12345)
                .city("somewhere")
                .color("blue")
                .build();

        Person person = modelMapper.map(personDto, Person.class);

        assertEquals(personDto.getId(), person.getId());
        assertEquals(personDto.getName(), person.getName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(expectedAddress, person.getAddress());
        assertEquals(expectedColor, person.getColor());
    }
}
