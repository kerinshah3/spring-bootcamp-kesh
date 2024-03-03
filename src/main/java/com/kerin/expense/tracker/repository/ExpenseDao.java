package com.kerin.expense.tracker.repository;

import com.kerin.expense.tracker.model.Transaction;

import java.util.List;

public interface ExpenseDao {

    Transaction save(Transaction transaction);
    Transaction findById(Long id);
    List<Transaction> findAll();
    void deleteById(Long id);
    Long update(Transaction transaction);
}
