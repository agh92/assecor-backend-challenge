package com.example.persons.utils;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DummyPersonBuilder {
  private DummyPersonBuilder() {}

  public static Person buildPerson() {
    return getJohnDoeBuilder().color(Color.BLUE).build();
  }

  public static Person buildPerson(Color color) {
    return getJohnDoeBuilder().color(color).build();
  }

  private static Person.PersonBuilder getJohnDoeBuilder() {
    return Person.builder().id(1L).name("john").lastName("doe").address("12345 somewhere");
  }

  public static List<Person> buildPerson(Integer number) {
    return IntStream.range(0, number).mapToObj(i -> buildPerson()).collect(Collectors.toList());
  }

  public static PersonDto buildPersonDto() {
    return getJohnDoeDtoBuilder().id(1L).build();
  }

  public static PersonDto buildPersonDtoWithoutId() {
    return getJohnDoeDtoBuilder().build();
  }

  private static PersonDto.PersonDtoBuilder getJohnDoeDtoBuilder() {
    return PersonDto.builder()
        .name("john")
        .lastName("doe")
        .zipCode(12345)
        .city(" somewhere")
        .color("blue");
  }
}
