package com.example.persons.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ColorStringValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsColor {
    String message() default "{com.example.persons.validation.validation.IsColor.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
