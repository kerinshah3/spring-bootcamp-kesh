package com.kerin.expense.tracker.validator;

import java.util.Map;

public interface ValidatorRule<T> {
    void validate(T t, Map<String , String> errorMap);
}
