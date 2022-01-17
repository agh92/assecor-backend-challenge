package com.example.persons.validation;

import com.example.persons.model.Color;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ColorStringValidator implements ConstraintValidator<IsColor, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value != null) {
            return validate(value);
        }
        return false;
    }

    private boolean validate(String value) {
        String upperCaseString = value.toUpperCase();
        Object[] enumValues = Color.class.getEnumConstants();

        return Arrays
                .stream(enumValues)
                .anyMatch(enumValue -> enumValue.toString().equals(upperCaseString));
    }

}
