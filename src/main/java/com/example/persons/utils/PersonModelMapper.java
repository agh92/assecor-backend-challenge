package com.example.persons.utils;

import com.example.persons.dto.PersonDto;
import com.example.persons.model.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

public class PersonModelMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Converter<String, String> ZipCodeAndCityToAddressConverter = context -> {
        PersonDto personDto = (PersonDto) context.getParent().getSource();
        return personDto.getZipCode() + " " + personDto.getCity();
    };

    private static final Converter<String, Integer> addressToZipCodeConverter = context -> {
        Person person = (Person) context.getParent().getSource();
        String zipCode = person.getAddress().substring(0,5);
        return Integer.parseInt(zipCode);
    };

    private static final Converter<String, String> addressToCityConverter = context -> {
        Person person = (Person) context.getParent().getSource();
        String address = person.getAddress();
        String city = address.substring(5);
        return city.trim();
    };

    static {
        modelMapper.typeMap(PersonDto.class, Person.class).addMappings(mapping -> {
            mapping.using(ZipCodeAndCityToAddressConverter).map(PersonDto::getCity, Person::setAddress);
        });

        modelMapper.typeMap(Person.class, PersonDto.class).addMappings(mapping -> {
            mapping.using(addressToCityConverter).map(Person::getAddress, PersonDto::setCity);
            mapping.using(addressToZipCodeConverter).map(Person::getAddress, PersonDto::setZipCode);
        });
    }

    public static Person map(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    public static PersonDto map(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }
}
