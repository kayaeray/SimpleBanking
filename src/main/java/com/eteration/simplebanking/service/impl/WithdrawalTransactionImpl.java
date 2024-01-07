package com.eteration.simplebanking.service.impl;
import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.dto.TransactionDTO;
import com.eteration.simplebanking.model.enums.TransactionTypes;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.service.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalTransactionImpl implements Transaction {
    private LocalDateTime date= LocalDateTime.now();

    @Override
    public AccountDTO execute(AccountDTO accountDTO, BigDecimal amount) {
        TransactionDTO transactionDTO = new TransactionDTO();
        if (accountDTO.getBalance().compareTo(amount) >= 0) {
            accountDTO.setBalance(accountDTO.getBalance().subtract(amount));
            if(accountDTO.getTransactions() == null){
                accountDTO.setTransactions(new ArrayList<>());
            }
            transactionDTO.setAmount(amount);
            transactionDTO.setDate(date);
            transactionDTO.setType(TransactionTypes.WithdrawalTransaction);
            transactionDTO.setApprovalCode(UUID.randomUUID().toString());
            accountDTO.getTransactions().add(transactionDTO);
        } else {
            throw new InsufficientBalanceException("Insufficient funds");
        }
        return accountDTO;
    }
}
