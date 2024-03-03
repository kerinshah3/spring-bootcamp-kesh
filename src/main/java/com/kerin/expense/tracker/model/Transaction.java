package com.kerin.expense.tracker.model;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    Long id;
    String description ;
    Double amount;
    String type;

}
