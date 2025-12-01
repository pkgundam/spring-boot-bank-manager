package com.bank.manager.dto;


import com.bank.manager.entity.TransactionEntity;
import com.bank.manager.entity.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionResponse {

    private Long transactionId;
    private Long accountId;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private Instant createdAt;
    private Long relatedAccountId;
    private String description;

    public TransactionResponse(Long transactionId,
                               Long accountId,
                               TransactionType type,
                               BigDecimal amount,
                               BigDecimal balanceAfter,
                               Instant createdAt,
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

    public static TransactionResponse from(TransactionEntity tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getRelatedAccountId(),
                tx.getType(),
                tx.getAmount(),
                tx.getBalanceAfter(),
                tx.getCreatedAt(),
                tx.getRelatedAccountId(),
                tx.getDescription()
        );
    }

}