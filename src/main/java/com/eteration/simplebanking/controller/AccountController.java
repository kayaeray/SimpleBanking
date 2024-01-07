package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.Constant;
import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.request.TransactionRequest;
import com.eteration.simplebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/v1")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/credit/{account}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String account, @RequestBody TransactionRequest request) {
        TransactionStatus transactionStatus = accountService.credit(account, request.getAmount());
        if (Constant.RESPONSE_OK.equals(transactionStatus.getStatus())) {
            return ResponseEntity.status(HttpStatus.OK).body(transactionStatus);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionStatus);
    }

    @PostMapping("/debit/{account}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String account, @RequestBody TransactionRequest request) {
        TransactionStatus transactionStatus = accountService.debit(account, request.getAmount());
        if (Constant.RESPONSE_OK.equals(transactionStatus.getStatus())) {
            return ResponseEntity.status(HttpStatus.OK).body(transactionStatus);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionStatus);
    }

    @GetMapping("/{account}")
    public ResponseEntity<AccountDTO> accountDetails(@PathVariable String account) {
        AccountDTO accountDTO = accountService.getAccountInfo(account);
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.ok().body(accountDTO);
    }
}
