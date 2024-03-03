package com.kerin.expense.tracker;

import com.kerin.expense.tracker.model.Transaction;
import com.kerin.expense.tracker.repository.ExpenseDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseTrackerRepositoryTest {

    @Autowired
    ExpenseDaoImpl expenseDao;

    @Test
    public void testSaveExpenseAndReturnExpense(){

        Transaction transaction = expenseDao.save(Transaction.builder()
                .type("income")
                .description("salary")
                .amount(1000D)
                .build());

        Transaction returnTransaction = expenseDao.findById(transaction.getId());

        Assertions.assertNotNull(returnTransaction);

    }

}
