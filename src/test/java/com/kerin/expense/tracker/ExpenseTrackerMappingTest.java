package com.kerin.expense.tracker;

import com.kerin.expense.tracker.dto.TransactionDto;
import com.kerin.expense.tracker.mapper.TransactionMapper;
import com.kerin.expense.tracker.model.Transaction;
import fr.xebia.extras.selma.Selma;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpenseTrackerMappingTest {

    @Test
    public void toDto(){
        Transaction transaction = Transaction.builder()
                .id(1L)
                .type("income")
                .description("salary")
                .amount(1000D)
                .build();

        TransactionMapper transactionMapper= Selma.getMapper(TransactionMapper.class);

        Assertions.assertEquals(transaction.getId(),transactionMapper.toDto(transaction).getId());
    }
    @Test
    public void toModel(){
        TransactionDto transactionDto = TransactionDto.builder()
                .id(1L)
                .type("income")
                .description("salary")
                .amount(1000D)
                .build();

        TransactionMapper transactionMapper= Selma.getMapper(TransactionMapper.class);

        Assertions.assertEquals(transactionDto.getId(),transactionMapper.toModel(transactionDto).getId());
    }
}
