package com.kerin.expense.tracker.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotZero.class)
@Documented
public @interface NonZero {
    String message() default "{ Zero is Not Valid }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
