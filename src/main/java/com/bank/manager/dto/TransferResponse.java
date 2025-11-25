package com.bank.manager.dto;

public class TransferResponse {

    private AccountResponse fromAccount;
    private AccountResponse toAccount;

    public TransferResponse(AccountResponse fromAccount, AccountResponse toAccount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public AccountResponse getFromAccount() {
        return fromAccount;
    }

    public AccountResponse getToAccount() {
        return toAccount;
    }
}