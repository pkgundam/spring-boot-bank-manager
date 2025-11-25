package com.bank.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CreateAccountRequest {

    @NotBlank(message = "Holder name is required")
    @Size(min = 2, max = 50, message = "Holder name must be between 2 and 50 characters")
    private String holderName;

    @PositiveOrZero(message = "Initial balance must be zero or positive")
    private BigDecimal initialBalance;

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

}