package com.example.persons.web.validation;

import com.example.persons.domain.Color;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ColorStringValidator implements ConstraintValidator<IsColor, String> {

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    var isValid = isValid(value);

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(String.format("%s is not a valid color", value))
          .addConstraintViolation();
    }

    return isValid;
  }

  private boolean isValid(String value) {
    if (value == null) {
      return false;
    }

    var upperCaseString = value.toUpperCase();
    var enumValues = Color.class.getEnumConstants();

    return Arrays.stream(enumValues)
        .anyMatch(enumValue -> enumValue.toString().equals(upperCaseString));
  }
}
