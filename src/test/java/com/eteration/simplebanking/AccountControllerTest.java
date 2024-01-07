package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.request.TransactionRequest;
import com.eteration.simplebanking.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    private MockMvc mockMvc;
    private AccountService accountService;
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        accountController = new AccountController(accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void credit_shouldReturnOkStatus() throws Exception {
        String account = "669-7788";
        BigDecimal amount = new BigDecimal("1000.0");
        TransactionRequest request = new TransactionRequest(amount);

        TransactionStatus transactionStatus = TransactionStatus.builder().status("OK").build();
        when(accountService.credit(account, amount)).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/credit/{account}", account)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(transactionStatus)));

        verify(accountService, times(1)).credit(account, amount);
    }

    @Test
    void debit_shouldReturnOkStatus() throws Exception {
        String account = "669-7788";
        BigDecimal amount = new BigDecimal("50.0");
        TransactionRequest request = new TransactionRequest(amount);

        TransactionStatus transactionStatus = TransactionStatus.builder().status("OK").build();
        when(accountService.debit(account, amount)).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/debit/{account}", account)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(transactionStatus)));

        verify(accountService, times(1)).debit(account, amount);
    }

    @Test
    void getAccountDetails_shouldReturnOkStatus() throws Exception {
        String account = "669-7788";
        AccountDTO accountDTO = new AccountDTO();
        when(accountService.getAccountInfo(account)).thenReturn(accountDTO);

        mockMvc.perform(get("/account/v1/{accountNumber}", account))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(accountDTO)));

        verify(accountService, times(1)).getAccountInfo(account);
    }

    @Test
    void credit_shouldReturnBadRequestStatus() throws Exception {
        String account = "669-7788";
        BigDecimal amount = BigDecimal.valueOf(-50.0); // Invalid amount for credit
        TransactionRequest request = new TransactionRequest(amount);

        TransactionStatus transactionStatus =  TransactionStatus.builder().status("Amount less than zero").build();
        when(accountService.credit(account, amount)).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/credit/{account}", account)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(transactionStatus)));

        verify(accountService, times(1)).credit(account, amount);
    }

    @Test
    void debit_shouldReturnBadRequestStatus() throws Exception {
        String account = "669-7788";
        BigDecimal amount = BigDecimal.valueOf(-50.0); // Invalid amount for debit
        TransactionRequest request = new TransactionRequest(amount);

        TransactionStatus transactionStatus = TransactionStatus.builder().status("Amount less than zero").build();
        when(accountService.debit(account, amount)).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/debit/{account}", account)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(transactionStatus)));

        verify(accountService, times(1)).debit(account, amount);
    }

    @Test
    void debit_shouldReturnAmountHigherThanBalanceStatus() throws Exception {
        String account = "669-7788";
        BigDecimal amount = new BigDecimal("500.0"); // Invalid amount higher than balance for debit
        TransactionRequest request = new TransactionRequest(amount);

        TransactionStatus transactionStatus =  TransactionStatus.builder().status("Balance less than amount").build();
        when(accountService.debit(account, amount)).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/debit/{account}", account)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(transactionStatus)));

        verify(accountService, times(1)).debit(account, amount);
    }
}
