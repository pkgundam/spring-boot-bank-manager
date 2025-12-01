package com.bank.manager.entity;

import com.bank.manager.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(schema = "bank", name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many transactions belong to one account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balanceAfter;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    // For transfers (nullable)
    @Column(name = "related_account_id")
    private Long relatedAccountId;

    @Column(length = 255)
    private String description;

    public TransactionEntity(TransactionType type,
                             BigDecimal amount,
                             BigDecimal balanceAfter,
                             Long relatedAccountId,
                             String description) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.relatedAccountId = relatedAccountId;
        this.description = description;
    }

}