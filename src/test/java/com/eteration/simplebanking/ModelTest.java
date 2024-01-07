package com.eteration.simplebanking;


import com.eteration.simplebanking.model.repository.TransactionRepository;
import com.eteration.simplebanking.provider.Account;
import com.eteration.simplebanking.provider.impl.AccountImpl;
import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;

import com.eteration.simplebanking.service.Transaction;
import com.eteration.simplebanking.model.factory.TransactionFactory;
import com.eteration.simplebanking.model.factory.impl.TransactionFactoryImpl;
import com.eteration.simplebanking.service.impl.BillPaymentTransactionImpl;
import com.eteration.simplebanking.service.impl.DepositTransactionImpl;
import com.eteration.simplebanking.service.impl.WithdrawalTransactionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private AccountDTO accountDTO;
    private Account account;

    private TransactionRepository transactionRepository;

    private TransactionFactory transactionFactory;

    @BeforeEach
    void setUp() {
        accountDTO = new AccountDTO();
        account = new AccountImpl();
        transactionFactory = new TransactionFactoryImpl();
    }

    @Test
    void testCreateAccountAndSetBalance0() {
        accountDTO.setOwner("Corbin Hamill");
        accountDTO.setAccountNumber("4567-345");
        accountDTO.setTransactions(new ArrayList<>());
        accountDTO.setBalance(BigDecimal.ZERO);
        assertEquals("Corbin Hamill", accountDTO.getOwner());
        assertEquals("4567-345", accountDTO.getAccountNumber());
        assertEquals(0, accountDTO.getBalance().compareTo(BigDecimal.ZERO));
    }

    @Test
    void testDepositIntoBankAccount() {
        accountDTO.setOwner("Claudie Durgan");
        accountDTO.setAccountNumber("345435-4354");
        accountDTO.setTransactions(new ArrayList<>());
        accountDTO.setBalance(BigDecimal.ZERO);
        account.post(transactionFactory.createDepositTransaction(), accountDTO, new BigDecimal(100));
        assertEquals(0, accountDTO.getBalance().compareTo(new BigDecimal(100)));
    }

    @Test
    void testWithdrawFromBankAccount() throws InsufficientBalanceException {
        accountDTO.setOwner("Etha Lehner");
        accountDTO.setAccountNumber("56546-566");
        accountDTO.setTransactions(new ArrayList<>());
        accountDTO.setBalance(BigDecimal.ZERO);
        account.post(transactionFactory.createDepositTransaction(),accountDTO, new BigDecimal(100));
        assertEquals(0, accountDTO.getBalance().compareTo(new BigDecimal(100)));
        account.post(transactionFactory.createWithdrawalTransaction(),accountDTO, new BigDecimal(50));
        assertEquals(0, accountDTO.getBalance().compareTo(new BigDecimal(50)));
    }

    @Test
    void testWithdrawException() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            accountDTO.setOwner("Annamarie Renner");
            accountDTO.setAccountNumber("454-54545");
            accountDTO.setTransactions(new ArrayList<>());
            accountDTO.setBalance(BigDecimal.ZERO);
            account.post(transactionFactory.createDepositTransaction(),accountDTO, new BigDecimal(100));
            account.post(transactionFactory.createWithdrawalTransaction(),accountDTO, new BigDecimal(500));
        });
    }

    @Test
    void testTransactions() {
        // Create account
        accountDTO.setOwner("Wilmer Wintheiser");
        accountDTO.setAccountNumber("45345-4355");
        accountDTO.setTransactions(new ArrayList<>());
        accountDTO.setBalance(BigDecimal.ZERO);
        assertEquals(0, accountDTO.getTransactions().size());

        // Deposit Transaction
        DepositTransactionImpl depositTrx = new DepositTransactionImpl();
        assertNotNull(depositTrx.getDate());
        account.post(depositTrx, accountDTO, new BigDecimal(100));
        assertEquals(0, accountDTO.getBalance().compareTo(new BigDecimal(100)));
        assertEquals(1, accountDTO.getTransactions().size());

        // Withdrawal Transaction
        WithdrawalTransactionImpl withdrawalTrx = new WithdrawalTransactionImpl();
        assertNotNull(withdrawalTrx.getDate());
        account.post(withdrawalTrx, accountDTO, new BigDecimal(60));
        assertEquals(0, accountDTO.getBalance().compareTo(new BigDecimal(40)));
        assertEquals(2, accountDTO.getTransactions().size());

        BillPaymentTransactionImpl billPaymentTransaction = new BillPaymentTransactionImpl();
        billPaymentTransaction.setPayee("Vodafone");
        billPaymentTransaction.setPhoneNumber("5423345566");
        assertNotNull(billPaymentTransaction.getDate());
        account.post(billPaymentTransaction, accountDTO, new BigDecimal(20));
        assertEquals(0, accountDTO.getBalance().compareTo(new BigDecimal(20)));
        assertEquals(3, accountDTO.getTransactions().size());
    }

    @Test
    void testPostMethod() {
        accountDTO.setOwner("Leonor McLaughlin");
        accountDTO.setAccountNumber("45645-454");
        accountDTO.setTransactions(new ArrayList<>());
        accountDTO.setBalance(BigDecimal.ZERO);
        TransactionFactory transactionFactory = new TransactionFactoryImpl();
        Transaction depositTransaction = transactionFactory.createDepositTransaction();
        Transaction withdrawalTransaction = transactionFactory.createWithdrawalTransaction();
        Transaction phoneBillPaymentTransaction = transactionFactory.createBillPaymentTransaction();
        account.post(depositTransaction, accountDTO, new BigDecimal(1000));
        account.post(withdrawalTransaction, accountDTO, new BigDecimal(200));
        account.post(phoneBillPaymentTransaction, accountDTO, new BigDecimal("96.50"));
        assertEquals(new BigDecimal("703.50"), accountDTO.getBalance());
    }
}

