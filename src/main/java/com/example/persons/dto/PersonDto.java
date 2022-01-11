package com.example.persons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private String lastName;
    private Integer zipCode;
    private String city;
    private String color;
}
