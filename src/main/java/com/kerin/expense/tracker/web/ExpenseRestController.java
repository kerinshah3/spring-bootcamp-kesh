package com.kerin.expense.tracker.web;

import com.kerin.expense.tracker.mapper.TransactionMapper;
import com.kerin.expense.tracker.service.ExpenseService;
import com.kerin.expense.tracker.dto.TransactionDto;
import com.kerin.expense.tracker.model.Transaction;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpenseRestController {

    @Autowired
    ExpenseService expenseService;

    TransactionMapper mapper = Selma.getMapper(TransactionMapper.class);

    @GetMapping("/rest/expense/{id}")
    public Transaction getExpense(@PathVariable Long id) {
        return expenseService.findById(id);
    }

    @PostMapping("/rest/expense")
    public Transaction saveExpense(@RequestBody TransactionDto transactionDto){
        Transaction transaction = mapper.toModel(transactionDto);
        return expenseService.save(transaction);
    }

}
