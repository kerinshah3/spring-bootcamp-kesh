package com.kerin.expense.tracker.service;

import com.kerin.expense.tracker.constant.ApplicationConstant;
import com.kerin.expense.tracker.model.Transaction;
import com.kerin.expense.tracker.repository.ExpenseDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class ExpenseService {

    @Autowired
    ExpenseDao expenseDao;

    String totalIncome;
    String totalExpenses;
    String totalBalance;

    public Transaction save(Transaction transaction) {
        transaction.setType(setTransactionType(transaction.getAmount()));

        return expenseDao.save(transaction);
    }

    public List<Transaction> findAll() {
        double income = 0;
        double expense = 0;
        double balance = 0;

        List<Transaction> transactionList = expenseDao.findAll();

        for (Transaction t : transactionList) {
            if (t.getType().equalsIgnoreCase(ApplicationConstant.INCOME)) {
                income = income + t.getAmount();

            } else if (t.getType().equalsIgnoreCase(ApplicationConstant.EXPENSE)) {
                expense = expense + t.getAmount();
            }
        }

        balance = income + expense;

        totalIncome = "+$" + income;
        totalExpenses = "$" + expense;
        totalBalance = "$" + balance;

        return transactionList;
    }

    public void deleteTransaction(Long id) {
        expenseDao.deleteById(id);
    }

    public Transaction findById(Long id) {
        return expenseDao.findById(id);
    }

    public Long update(Transaction transaction) {
        transaction.setType(setTransactionType(transaction.getAmount()));
        return expenseDao.update(transaction);
    }

    public String setTransactionType(double amount) {
        if (amount < 0) {
            return ApplicationConstant.EXPENSE;
        } else {
            return ApplicationConstant.INCOME;
        }
    }
}
