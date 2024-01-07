package com.eteration.simplebanking.provider;

import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.service.Transaction;

import java.math.BigDecimal;

public interface Account {
    AccountDTO post(Transaction transaction, AccountDTO accountDTO, BigDecimal amount);
}
