package com.eteration.simplebanking.provider.impl;


import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.provider.Account;
import com.eteration.simplebanking.service.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountImpl implements Account {

    @Override
    public AccountDTO post(Transaction transaction, AccountDTO accountDTO, BigDecimal amount) {
        return transaction.execute(accountDTO,amount);
    }
}

