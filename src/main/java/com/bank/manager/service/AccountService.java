package com.bank.manager.service;

import com.bank.manager.dto.*;

import java.util.List;

/**
 * Business logic for managing bank accounts.
 */
public interface AccountService {

    /**
     * Creates a new bank account with the provided details.
     *
     * @param request The request containing account creation details
     * @return The created account response
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    AccountResponse createAccount(CreateAccountRequest request);

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param accountId The ID of the account to retrieve
     * @return The account details
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     */
    AccountResponse getAccountById(Long accountId);

    /**
     * Retrieves all bank accounts in the system.
     *
     * @return A list of all account responses
     */
    List<AccountResponse> getAllAccounts();

    /**
     * Deposits the specified amount into the account.
     *
     * @param accountId The ID of the account to deposit into
     * @param request The request containing the deposit amount
     * @return The updated account details
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    AccountResponse deposit(Long accountId, AmountRequest request);

    /**
     * Withdraws the specified amount from the account.
     *
     * @param accountId The ID of the account to withdraw from
     * @param request The request containing the withdrawal amount
     * @return The updated account details
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     * @throws com.bank.manager.exception.InsufficientBalanceException if the account has insufficient funds
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    AccountResponse withdraw(Long accountId, AmountRequest request);

    /**
     * Transfers money between two accounts.
     *
     * @param request The transfer request containing source account, destination account, and amount
     * @return The transfer response containing both updated account details
     * @throws com.bank.manager.exception.AccountNotFoundException if either account is not found
     * @throws com.bank.manager.exception.InsufficientBalanceException if the source account has insufficient funds
     * @throws IllegalArgumentException if source and destination accounts are the same
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    TransferResponse transfer(TransferRequest request);

    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId The ID of the account to get transactions for
     * @return A list of transaction responses
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     */
    List<TransactionResponse> getTransactionsForAccount(Long accountId);

}