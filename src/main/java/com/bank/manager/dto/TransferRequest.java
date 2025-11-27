package com.bank.manager.dto;

import com.bank.manager.validation.DifferentAccounts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@DifferentAccounts(
    first = "fromAccountId",
    second = "toAccountId",
    message = "Source and destination accounts must be different"
)
public class TransferRequest {

    @NotNull(message = "From account id is required")
    private Long fromAccountId;

    @NotNull(message = "To account id is required")
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}