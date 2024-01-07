package com.eteration.simplebanking.model.dto;

import com.eteration.simplebanking.model.enums.TransactionTypes;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private LocalDateTime date;
    private BigDecimal amount;
    private TransactionTypes type;
    private String approvalCode;
}
