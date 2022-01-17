package com.example.persons.model.mapper;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

public class ModelMapperFactory {

  public ModelMapper createModelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    Converter<String, String> zipCodeAndCityToAddressConverter =
        context -> {
          PersonDto personDto = (PersonDto) context.getParent().getSource();
          return personDto.getZipCode() + " " + personDto.getCity();
        };

    Converter<String, Integer> addressToZipCodeConverter =
        context -> {
          Person person = (Person) context.getParent().getSource();
          String address = person.getAddress().trim();
          String[] addressParts = address.split("\\s", 2);
          String zipCode = addressParts[0];
          return Integer.parseInt(zipCode);
        };

    Converter<String, String> addressToCityConverter =
        context -> {
          Person person = (Person) context.getParent().getSource();
          String address = person.getAddress().trim();
          String[] addressParts = address.split("\\s", 2);
          String city = addressParts[1];
          return city.trim();
        };

    Converter<String, Color> stringToColorConverter =
        context -> {
          PersonDto person = (PersonDto) context.getParent().getSource();
          return Color.valueOf(person.getColor().toUpperCase());
        };

    Converter<Color, String> colorToStringConverter =
        context -> {
          Person person = (Person) context.getParent().getSource();
          return person.getColor().name().toLowerCase();
        };

    modelMapper
        .typeMap(PersonDto.class, Person.class)
        .addMappings(
            mapping -> {
              mapping
                  .using(zipCodeAndCityToAddressConverter)
                  .map(PersonDto::getCity, Person::setAddress);
              mapping.using(stringToColorConverter).map(PersonDto::getColor, Person::setColor);
            });

    modelMapper
        .typeMap(Person.class, PersonDto.class)
        .addMappings(
            mapping -> {
              mapping.using(addressToCityConverter).map(Person::getAddress, PersonDto::setCity);
              mapping
                  .using(addressToZipCodeConverter)
                  .map(Person::getAddress, PersonDto::setZipCode);
              mapping.using(colorToStringConverter).map(Person::getColor, PersonDto::setColor);
            });

    return modelMapper;
  }
}
