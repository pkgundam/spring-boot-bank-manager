package com.bank.manager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    private Long accountId;
    private String accountName;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private Long ownerUserId;

    public Account() {
    }

    public Account(Long accountId, String accountName, BigDecimal balance, LocalDateTime createdAt, Long ownerUserId) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
        this.createdAt = createdAt;
        this.ownerUserId = ownerUserId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

}