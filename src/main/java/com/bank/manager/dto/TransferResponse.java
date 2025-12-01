package com.bank.manager.dto;

import lombok.Data;

@Data
public class TransferResponse {

    private AccountResponse fromAccount;
    private AccountResponse toAccount;

    public TransferResponse(AccountResponse fromAccount, AccountResponse toAccount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

}