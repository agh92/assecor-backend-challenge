package com.example.persons.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private Long id;
    private String name;
    private String lastName;
    private String address;
    private Color color;
}
