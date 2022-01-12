package com.example.persons.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    private String name;
    private String lastName;
    private Integer zipCode;
    private String city;
    private ColorDto color;
}
