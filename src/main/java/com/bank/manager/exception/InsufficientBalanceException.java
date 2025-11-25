package com.bank.manager.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(BigDecimal currentBalance, BigDecimal requestedAmount) {
        super("Insufficient balance. Current balance: " + currentBalance + ", requested: " + requestedAmount);
    }

}