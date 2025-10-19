package com.cheong.agenticai.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Slf4j
public class MinuteRangeValidator implements
        ConstraintValidator<MinuteRange, LocalDateTime> {

    private int value;

    @Override
    public void initialize(MinuteRange constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDateTime v, ConstraintValidatorContext constraintValidatorContext) {
        if (v == null) {
            return true;
        }

        return !v.isBefore(LocalDateTime.now().plusMinutes(value));
    }
}
