package com.kerin.expense.tracker.exception;

import java.util.HashMap;
import java.util.Map;

public class InvalidFieldDataException extends RuntimeException{

    private Map<Long,String> errorMap = new HashMap<>();

    public InvalidFieldDataException() {
        super();
    }
    public InvalidFieldDataException(Map<Long,String> errorMap) {
        this.errorMap = errorMap;
    }

    public Map<Long, String> getErrorMap() {
        return errorMap;
    }

    @Override
    public String toString() {
        return "InvalidFieldDataException{ Field Error " +
                "errorMap=" + errorMap.get(1) +
                '}';
    }

}