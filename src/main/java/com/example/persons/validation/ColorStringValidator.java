package com.example.persons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ColorStringValidator implements ConstraintValidator<EnumValue, String> {

    private Object[] enumValues;

    @Override
    public void initialize(final EnumValue annotation) {
        enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value != null) {
            String upperCaseString = value.toUpperCase();
            return validate(upperCaseString);
        }
        return false;
    }

    private boolean validate(String value) {
        String upperCaseString = value.toUpperCase();
        for (Object enumValue : enumValues) {
            if (enumValue.toString().equals(upperCaseString)) {
                return true;
            }
        }
        return false;
    }

}
