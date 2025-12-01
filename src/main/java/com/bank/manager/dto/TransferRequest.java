package com.bank.manager.dto;

import com.bank.manager.validation.DifferentAccounts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
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

}