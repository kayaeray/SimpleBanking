package com.eteration.simplebanking.model.factory;

import com.eteration.simplebanking.service.Transaction;
import org.springframework.stereotype.Component;

@Component
public interface TransactionFactory {
    Transaction createDepositTransaction();
    Transaction createWithdrawalTransaction();
    Transaction createBillPaymentTransaction();
}
