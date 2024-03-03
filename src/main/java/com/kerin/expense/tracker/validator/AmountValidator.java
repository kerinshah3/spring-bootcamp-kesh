package com.kerin.expense.tracker.validator;

import com.kerin.expense.tracker.dto.TransactionDto;

import java.util.Map;

public class AmountValidator  implements ValidatorRule<TransactionDto> {

    @Override
    public void validate(TransactionDto transactionDto, Map<String, String> errorMap) {
        if(transactionDto.getType().equalsIgnoreCase("expense") && transactionDto.getAmount() > 0) {
            errorMap.put("Amount","Amount should be Negative for Expense (Add + OR - to Identify its Income or Expense )");
        }
    }
}
