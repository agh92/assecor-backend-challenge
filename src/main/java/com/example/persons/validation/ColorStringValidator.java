package com.example.persons.validation;

import com.example.persons.model.Color;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ColorStringValidator implements ConstraintValidator<IsColor, String> {

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    boolean isValid = isValid(value);

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

    String upperCaseString = value.toUpperCase();
    Object[] enumValues = Color.class.getEnumConstants();

    return Arrays.stream(enumValues)
        .anyMatch(enumValue -> enumValue.toString().equals(upperCaseString));
  }
}
