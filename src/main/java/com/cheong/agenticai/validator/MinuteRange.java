package com.cheong.agenticai.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {MinuteRangeValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinuteRange {

    int value() default 2;
    String message() default "You cannot book slots if the start time is less than 15 minutes from now";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
