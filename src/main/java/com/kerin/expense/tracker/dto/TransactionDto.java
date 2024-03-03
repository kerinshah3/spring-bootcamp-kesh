package com.kerin.expense.tracker.dto;

import com.kerin.expense.tracker.validator.NonZero;
import lombok.*;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    Long id;

    @NotEmpty(message = "Expense Description cannot be empty.")
    String description;

    @NonZero
    Double amount;

    String type;

}
