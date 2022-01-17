package com.example.persons.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {BeansConfiguration.class})
public class ModelMapperTest {

  @Autowired private ModelMapper modelMapper;

  @BeforeEach
  void setup() {
    // skip null values to avoid defining all properties in the tests
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  @Test
  void mapsPersonIdCorrectlyToPersonDtoId() {
    // given
    Person person = Person.builder().id(1L).build();

    // when
    PersonDto personDto = modelMapper.map(person, PersonDto.class);

    // then
    assertEquals(person.getId(), personDto.getId());
  }

  @Test
  void mapsPersonNameCorrectlyToPersonDtoName() {
    // given
    Person person = Person.builder().name("john").build();

    // when
    PersonDto personDto = modelMapper.map(person, PersonDto.class);

    // then
    assertEquals(person.getName(), personDto.getName());
  }

  @Test
  void mapsPersonLastNameCorrectlyToPersonDtoLastName() {
    // given
    Person person = Person.builder().lastName("doe").build();

    // when
    PersonDto personDto = modelMapper.map(person, PersonDto.class);

    // then
    assertEquals(person.getLastName(), personDto.getLastName());
  }

  @Test
  void mapsColorToLowerCaseStringColor() {
    // given
    Color expectedColor = Color.BLUE;
    Person person = Person.builder().color(expectedColor).build();

    // when
    PersonDto personDto = modelMapper.map(person, PersonDto.class);

    // then
    assertEquals(expectedColor.name().toLowerCase(), personDto.getColor());
  }

  @Test
  void mapsPersonAddressCorrectlyToPersonDtoZipCodeAndCity() {
    // given
    Integer expectedZipCode = 12345;
    String expectedCity = "somewhere";

    Person person = Person.builder().address(expectedZipCode + " " + expectedCity).build();

    // when
    PersonDto personDto = modelMapper.map(person, PersonDto.class);

    // then
    assertEquals(expectedZipCode, personDto.getZipCode());
    assertEquals(expectedCity, personDto.getCity());
  }

  @Test
  void mapsPersonDtoIdCorrectlyToPersonId() {
    // given
    PersonDto personDto = PersonDto.builder().id(1L).build();

    // when
    Person person = modelMapper.map(personDto, Person.class);

    // then
    assertEquals(personDto.getId(), person.getId());
  }

  @Test
  void mapsPersonDtoNameCorrectlyToPersonName() {
    // given
    PersonDto personDto = PersonDto.builder().name("john").build();

    // when
    Person person = modelMapper.map(personDto, Person.class);

    // then
    assertEquals(personDto.getName(), person.getName());
  }

  @Test
  void mapsPersonDtoLastNameCorrectlyToPersonLastName() {
    // given
    PersonDto personDto = PersonDto.builder().lastName("doe").build();

    // when
    Person person = modelMapper.map(personDto, Person.class);

    // then
    assertEquals(personDto.getLastName(), person.getLastName());
  }

  @Test
  void mapsPersonDtoStringColorCorrectlyToColor() {
    // given
    Color expectedColor = Color.BLUE;
    PersonDto personDto = PersonDto.builder().color(expectedColor.name().toLowerCase()).build();

    // when
    Person person = modelMapper.map(personDto, Person.class);

    // then
    assertEquals(expectedColor, person.getColor());
  }

  @Test
  void mapsPersonDtoZipCodeAndCityCorrectlyToPersonAddress() {
    // given
    String expectedAddress = "12345 somewhere";
    PersonDto personDto = PersonDto.builder().zipCode(12345).city("somewhere").build();

    // when
    Person person = modelMapper.map(personDto, Person.class);

    // then
    assertEquals(expectedAddress, person.getAddress());
  }
}
