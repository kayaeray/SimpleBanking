package com.eteration.simplebanking.service;


import com.eteration.simplebanking.model.dto.AccountDTO;

import java.math.BigDecimal;

public interface Transaction {
    AccountDTO execute(AccountDTO account, BigDecimal amount);
}
