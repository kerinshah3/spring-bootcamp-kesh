package com.kerin.expense.tracker.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotZero implements ConstraintValidator<NonZero, Double> {
    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
       return  aDouble != null &&  aDouble != 0;
    }
}
