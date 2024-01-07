package com.eteration.simplebanking.model.factory.impl;

import com.eteration.simplebanking.service.Transaction;
import com.eteration.simplebanking.model.factory.TransactionFactory;
import com.eteration.simplebanking.service.impl.BillPaymentTransactionImpl;
import com.eteration.simplebanking.service.impl.DepositTransactionImpl;
import com.eteration.simplebanking.service.impl.WithdrawalTransactionImpl;
import org.springframework.stereotype.Component;

@Component
public class TransactionFactoryImpl implements TransactionFactory {
    @Override
    public Transaction createDepositTransaction() {
        return new DepositTransactionImpl();
    }

    @Override
    public Transaction createWithdrawalTransaction() {
        return new WithdrawalTransactionImpl();
    }

    @Override
    public Transaction createBillPaymentTransaction() {
        return new BillPaymentTransactionImpl();
    }
}
