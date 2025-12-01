package com.bank.manager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class AccountResponse {

    private Long accountId;
    private String holderName;
    private BigDecimal balance;
    private Instant createdAt;

    public AccountResponse(Long accountId, String holderName, BigDecimal balance, Instant createdAt) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.createdAt = createdAt;
    }

}