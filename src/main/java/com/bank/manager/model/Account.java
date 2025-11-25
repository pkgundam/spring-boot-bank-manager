package com.bank.manager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    private Long accountId;
    private String holderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public Account() {
    }

    public Account(Long accountId, String holderName, BigDecimal balance, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}