package com.bank.manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponse {

    private Long accountId;
    private String holderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public AccountResponse(Long accountId, String holderName, BigDecimal balance, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}