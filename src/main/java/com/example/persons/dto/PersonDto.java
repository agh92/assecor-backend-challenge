package com.example.persons.dto;

import com.example.persons.model.Color;
import com.example.persons.validation.EnumValue;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    @NotBlank
    @Pattern(regexp = "\\D[ \\-\\D]*")
    private String name;
    @NotBlank
    @Pattern(regexp = "[\\D][ \\-\\D]*")
    private String lastName;
    @Positive
    private Integer zipCode;
    @NotBlank
    @Pattern(regexp = "[\\D][ \\-\\D]*")
    private String city;
    @EnumValue(enumClass = Color.class, message = "Invalid color")
    private String color;
}
