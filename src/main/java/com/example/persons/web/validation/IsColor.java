package com.example.persons.web.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {ColorStringValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsColor {
  String message() default "{com.example.persons.validation.validation.IsColor.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
