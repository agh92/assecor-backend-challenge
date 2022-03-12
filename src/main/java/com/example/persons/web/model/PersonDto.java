package com.example.persons.web.model;

import com.example.persons.web.validation.IsColor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDto {
  @Null private Long id;

  @NotBlank
  @Pattern(regexp = "\\D[ \\-\\D]*")
  private String name;

  @NotBlank
  @Pattern(regexp = "[\\D][ \\-\\D]*")
  private String lastName;

  @NotNull @Positive private Integer zipCode;

  @NotBlank
  @Pattern(regexp = "[\\D][ \\-\\D]*")
  private String city;

  @IsColor private String color;
}
