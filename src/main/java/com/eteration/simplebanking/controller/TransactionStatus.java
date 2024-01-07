package com.eteration.simplebanking.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class TransactionStatus {
    private String status;
    private String approvalCode;
}
