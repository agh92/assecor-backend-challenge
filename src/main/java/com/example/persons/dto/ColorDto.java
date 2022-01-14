package com.example.persons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ColorDto {
    BLUE("blue"),
    GREEN("green"),
    VIOLET("violet"),
    RED("red"),
    YELLOW("yellow"),
    TURQUOISE("turquoise"),
    WHITE("white");

    @Getter
    private final String value;
}
