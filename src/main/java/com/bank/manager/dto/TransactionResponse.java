package com.bank.manager.dto;

import com.bank.manager.model.Transaction;
import com.bank.manager.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long transactionId;
    private Long accountId;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;
    private Long relatedAccountId;
    private String description;

    public TransactionResponse(Long transactionId,
                               Long accountId,
                               TransactionType type,
                               BigDecimal amount,
                               BigDecimal balanceAfter,
                               LocalDateTime createdAt,
                               Long relatedAccountId,
                               String description) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
        this.relatedAccountId = relatedAccountId;
        this.description = description;
    }

    public static TransactionResponse from(Transaction tx) {
        return new TransactionResponse(
                tx.getTransactionId(),
                tx.getAccountId(),
                tx.getType(),
                tx.getAmount(),
                tx.getBalanceAfter(),
                tx.getCreatedAt(),
                tx.getRelatedAccountId(),
                tx.getDescription()
        );
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getRelatedAccountId() {
        return relatedAccountId;
    }

    public String getDescription() {
        return description;
    }

}