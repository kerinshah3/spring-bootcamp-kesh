package com.kerin.expense.tracker;

import com.kerin.expense.tracker.model.Transaction;
import com.kerin.expense.tracker.service.ExpenseService;
import com.kerin.expense.tracker.repository.ExpenseDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseTrackerServiceTest {

    @Autowired
    ExpenseService expenseService;

    @MockBean
    ExpenseDaoImpl expenseDao;

    @Test
    public void getExpenseById(){
        BDDMockito
                .given(expenseDao.findById(ArgumentMatchers.anyLong()))
                .willReturn(Transaction.builder()
                        .amount(-200D)
                        .description("dinner")
                        .type("expense")
                        .build());
        Transaction transaction = expenseService.findById(1L);
        Assertions.assertNotNull(transaction);
    }

}
