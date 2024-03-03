package com.kerin.expense.tracker;

import com.kerin.expense.tracker.dto.TransactionDto;
import com.kerin.expense.tracker.validator.AmountValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ExpenseTrackerJunitTest {

    @Test
    public void TestWithCorrectAmount(){
        TransactionDto transaction = TransactionDto.builder()
                .id(1L)
                .amount(-1000D)
                .description("Bike")
                .type("expense")
                .build();

        Map<String,String> errorMap = new HashMap<>();

        AmountValidator amountValidator = new AmountValidator();
        amountValidator.validate(transaction,errorMap);

        Assertions.assertTrue(errorMap.size() == 0, "there has to be no error");

    }


    @Test
    public void TestWithWrongAmount(){

        TransactionDto transactionDto = TransactionDto.builder()
                .id(1L)
                .amount(1000D)
                .description("Bike")
                .type("expense")
                .build();

        Map<String,String> errorMap = new HashMap<>();

        AmountValidator amountValidator = new AmountValidator();
        amountValidator.validate(transactionDto,errorMap);

        Assertions.assertTrue(errorMap.size() == 1, "there has to be one error");

    }
}
