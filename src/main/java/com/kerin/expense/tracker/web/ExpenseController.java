package com.kerin.expense.tracker.web;

import com.kerin.expense.tracker.constant.ApplicationConstant;
import com.kerin.expense.tracker.dto.TransactionDto;
import com.kerin.expense.tracker.mapper.TransactionMapper;
import com.kerin.expense.tracker.model.Transaction;
import com.kerin.expense.tracker.service.ExpenseService;
import com.kerin.expense.tracker.repository.ExpenseDaoImpl;
import fr.xebia.extras.selma.Selma;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class ExpenseController {

    @Autowired
    ExpenseDaoImpl expenseDao;

    @Autowired
    ExpenseService expenseService;

    TransactionMapper mapper = Selma.getMapper(TransactionMapper.class);

    @GetMapping("/home")
    public String homePage(Model model) {
        log.info("home Page");
        model.addAttribute("expenses", expenseService.findAll());
        model.addAttribute("income", expenseService.getTotalIncome());
        model.addAttribute("totalExpense", expenseService.getTotalExpenses());
        model.addAttribute("balance", expenseService.getTotalBalance());
        model.addAttribute("transactionDto", new TransactionDto());
        return "index";
    }

    @PostMapping("/expense")
    public String saveExpense(@Valid TransactionDto transactionDto,BindingResult result,Model model) {
        log.info("saving started");
        if (result.hasErrors()) {
            log.info("Error found");
            model.addAttribute("expenses", expenseService.findAll());
            model.addAttribute("income", expenseService.getTotalIncome());
            model.addAttribute("totalExpense", expenseService.getTotalExpenses());
            model.addAttribute("balance", expenseService.getTotalBalance());
            return "index";
        }
        log.info("Error not found");
        Transaction transaction = mapper.toModel(transactionDto);
        expenseService.save(transaction);
        return ApplicationConstant.REDIRECT_HOME;
    }

    @PostMapping("/update")
    public String updateExpense(@Valid TransactionDto transactionDto, BindingResult result, Model model ) {
        log.info("Updating Transaction");
        if(result.hasErrors()){
            log.info("Error Found");
            return "expense-update";
        }
        log.info("No Error Found");
        Transaction transaction = mapper.toModel(transactionDto);
        expenseService.update(transaction);
        return ApplicationConstant.REDIRECT_HOME;
    }

    @GetMapping("/expense/update/{id}")
    public String updateTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("transactionDto", expenseService.findById(id));
        return "expense-update";
    }

    @GetMapping("/expense/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        expenseService.deleteTransaction(id);
        return ApplicationConstant.REDIRECT_HOME;
    }

    @GetMapping("/expense/view/{id}")
    public String viewTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("transaction", expenseService.findById(id));
        return "expense-view";
    }


}
