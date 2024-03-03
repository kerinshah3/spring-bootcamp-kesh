package com.kerin.expense.tracker.mapper;

import com.kerin.expense.tracker.dto.TransactionDto;
import com.kerin.expense.tracker.model.Transaction;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface TransactionMapper {

    TransactionDto toDto(Transaction transaction);

    Transaction toModel(TransactionDto transactionDto);
}
