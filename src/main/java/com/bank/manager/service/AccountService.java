package com.bank.manager.service;

import com.bank.manager.dto.*;

import java.util.List;

/**
 * Business logic for managing bank accounts.
 */
public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccountById(Long accountId);

    List<AccountResponse> getAllAccounts();

    AccountResponse deposit(Long accountId, AmountRequest request);

    AccountResponse withdraw(Long accountId, AmountRequest request);

    TransferResponse transfer(TransferRequest request);

    List<TransactionResponse> getTransactionsForAccount(Long accountId);

}