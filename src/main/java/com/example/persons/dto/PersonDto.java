package com.example.persons.dto;

import com.example.persons.validation.IsColor;
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
    @IsColor(message = "invalid color")
    private String color;
}
