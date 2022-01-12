package com.example.persons;


import com.example.persons.dto.ColorDto;
import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.example.persons.utils.PersonModelMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonModelMapperTest {

    @Test
    void mapsPersonToPersonDto() {
        ColorDto expectedColor = ColorDto.BLUE;
        Integer expectedZipCode = 12345;
        String expectedCity = "somewhere";

        Person person = Person.builder()
                .id(1L)
                .name("john")
                .lastName("doe")
                .address(expectedZipCode + " " + expectedCity)
                .color(Color.BLUE)
                .build();

        PersonDto personDto = PersonModelMapper.map(person);

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
                .color(ColorDto.BLUE)
                .build();

        Person person = PersonModelMapper.map(personDto);

        assertEquals(personDto.getId(), person.getId());
        assertEquals(personDto.getName(), person.getName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(expectedAddress, person.getAddress());
        assertEquals(expectedColor, person.getColor());
    }
}
