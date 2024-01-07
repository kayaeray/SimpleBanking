
package com.eteration.simplebanking.service.impl;


import com.eteration.simplebanking.Constant;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.factory.TransactionFactory;
import com.eteration.simplebanking.model.mapper.AccountMapper;
import com.eteration.simplebanking.model.mapper.TransactionMapper;
import com.eteration.simplebanking.provider.Account;
import com.eteration.simplebanking.model.entity.AccountEntity;
import com.eteration.simplebanking.model.repository.AccountRepository;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.Transaction;
import com.eteration.simplebanking.validation.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final Account accountProvider;
    private final AccountValidation accountValidation;

    private final TransactionFactory transactionFactory;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, Account accountProvider, AccountValidation accountValidation, TransactionFactory transactionFactory) {
        this.accountRepository = accountRepository;
        this.accountProvider = accountProvider;
        this.accountValidation = accountValidation;
        this.transactionFactory = transactionFactory;
    }

    @Override
    @Transactional
    public TransactionStatus credit(String account, BigDecimal amount) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByAccountNumber(account);
        if (optionalAccount.isEmpty()) {
            return TransactionStatus.builder().status(String.format("Account not found. Account number: %s", account)).build();
        }

        AccountEntity accountEntity = optionalAccount.get();
        if(accountValidation.isAmountLessThanZero(amount) != null){
            return accountValidation.isAmountLessThanZero(amount);
        }

        try {
            Transaction depositTransaction = transactionFactory.createDepositTransaction();
            AccountDTO accountDTO = accountProvider.post(depositTransaction,AccountMapper.INSTANCE.accountToDto(accountEntity), amount);
            accountEntity.setBalance(accountDTO.getBalance());
            accountEntity.addTransaction(TransactionMapper.INSTANCE.dtoToTransaction(accountDTO.getTransactions().get(accountDTO.getTransactions().size() - 1)));
            accountRepository.save(accountEntity);
            return new TransactionStatus(Constant.RESPONSE_OK, accountDTO.getTransactions().get(accountDTO.getTransactions().size() - 1).getApprovalCode());
        } catch (Exception e) {
            return TransactionStatus.builder().status("Error during deposit").build();
        }
    }

    @Override
    public TransactionStatus debit(String account, BigDecimal amount) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByAccountNumber(account);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(String.format("Account not found, account number: %s", account));
        }

        AccountEntity accountEntity = optionalAccount.get();

        if(accountValidation.isAmountLessThanZero(amount) != null){
            return accountValidation.isAmountLessThanZero(amount);
        }
        if(accountValidation.isBalanceLessThanAmount(accountEntity.getBalance(), amount) != null){
            return accountValidation.isBalanceLessThanAmount(accountEntity.getBalance(), amount);
        }

        try {
            Transaction withdrawalTransaction = transactionFactory.createWithdrawalTransaction();
            AccountDTO accountDTO = accountProvider.post(withdrawalTransaction, AccountMapper.INSTANCE.accountToDto(accountEntity), amount);
            accountEntity.setBalance(accountDTO.getBalance());
            accountEntity.addTransaction(TransactionMapper.INSTANCE.dtoToTransaction(accountDTO.getTransactions().get(accountDTO.getTransactions().size() - 1)));
            accountRepository.save(accountEntity);
            return new TransactionStatus(Constant.RESPONSE_OK, accountDTO.getTransactions().get(accountDTO.getTransactions().size() - 1).getApprovalCode());
        } catch (Exception e) {
            return TransactionStatus.builder().status("Error during deposit").build();
        }
    }

    @Override
    public AccountDTO getAccountInfo(String account) {
        Optional<AccountEntity> accountEntity = accountRepository.findByAccountNumber(account);
        return accountEntity.map(AccountMapper.INSTANCE::accountToDto).orElse(null);
    }
}