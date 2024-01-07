package com.eteration.simplebanking.service;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.dto.AccountDTO;

import java.math.BigDecimal;

public interface AccountService {
    TransactionStatus credit(String account, BigDecimal amount);

    TransactionStatus debit(String account, BigDecimal amount);

    AccountDTO getAccountInfo(String account);
}
