package com.example.persons.configuration;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Color;
import com.example.persons.model.Person;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper
                .builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> ZipCodeAndCityToAddressConverter = context -> {
            PersonDto personDto = (PersonDto) context.getParent().getSource();
            return personDto.getZipCode() + " " + personDto.getCity();
        };

        Converter<String, Integer> addressToZipCodeConverter = context -> {
            Person person = (Person) context.getParent().getSource();
            String zipCode = person.getAddress().substring(0, 5);
            return Integer.parseInt(zipCode);
        };

        Converter<String, String> addressToCityConverter = context -> {
            Person person = (Person) context.getParent().getSource();
            String address = person.getAddress();
            String city = address.substring(5);
            return city.trim();
        };

        Converter<String, Color> stringToColorConverter = context -> {
            PersonDto person = (PersonDto) context.getParent().getSource();
            return Color.valueOf(person.getColor().toUpperCase());
        };

        Converter<Color, String> colorToStringConverter = context -> {
            Person person = (Person) context.getParent().getSource();
            return person.getColor().name().toLowerCase();
        };

        modelMapper.typeMap(PersonDto.class, Person.class).addMappings(mapping -> {
            mapping.using(ZipCodeAndCityToAddressConverter).map(PersonDto::getCity, Person::setAddress);
            mapping.using(stringToColorConverter).map(PersonDto::getColor,Person::setColor);

        });

        modelMapper.typeMap(Person.class, PersonDto.class).addMappings(mapping -> {
            mapping.using(addressToCityConverter).map(Person::getAddress, PersonDto::setCity);
            mapping.using(addressToZipCodeConverter).map(Person::getAddress, PersonDto::setZipCode);
            mapping.using(colorToStringConverter).map(Person::getColor,PersonDto::setColor);
        });

        return modelMapper;
    }
}
