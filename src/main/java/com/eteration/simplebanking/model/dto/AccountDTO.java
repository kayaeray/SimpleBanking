package com.eteration.simplebanking.model.dto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AccountDTO {
    private Long id;
    private BigDecimal balance;
    private String owner;
    private String accountNumber;
    private LocalDateTime createdDate;
    private List<TransactionDTO> transactions;
}
