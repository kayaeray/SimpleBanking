package com.eteration.simplebanking.validation;

import com.eteration.simplebanking.controller.TransactionStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component
@Service
public class AccountValidation {


    public TransactionStatus isAmountLessThanZero(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            return TransactionStatus.builder().status("Amount less than zero").build();
        }
        return null;
    }

    public TransactionStatus isBalanceLessThanAmount(BigDecimal balance, BigDecimal amount){
        if(balance.compareTo(amount) < 0){
            return TransactionStatus.builder().status("Balance less than amount").build();
        }
        return null;
    }
}