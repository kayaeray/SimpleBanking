package com.eteration.simplebanking.service.impl;
import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.service.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillPaymentTransactionImpl implements Transaction {
    private LocalDateTime date;
    private String payee;
    private String phoneNumber;

    public BillPaymentTransactionImpl(){
        this.date = LocalDateTime.now();
    }

    @Override
    public AccountDTO execute(AccountDTO accountDTO, BigDecimal amount) {
        if (accountDTO.getBalance().compareTo(amount) >= 0) {
            accountDTO.setBalance(accountDTO.getBalance().subtract(amount));
            accountDTO.getTransactions().add(null);
        } else {
            throw new InsufficientBalanceException("Insufficient funds");
        }
        return accountDTO;
    }
}
