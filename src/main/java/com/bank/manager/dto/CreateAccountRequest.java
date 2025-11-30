package com.bank.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CreateAccountRequest {

    @NotBlank(message = "Account name is required")
    @Size(min = 2, max = 50, message = "Holder name must be between 2 and 50 characters")
    private String accountName;

    @PositiveOrZero(message = "Initial balance must be zero or positive")
    @NotNull
    private BigDecimal initialBalance;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

}