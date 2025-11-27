package com.bank.manager.controller;

import com.bank.manager.dto.*;
import com.bank.manager.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Creates a new bank account with the provided details.
     *
     * @param request The request containing account creation details
     * @return ResponseEntity containing the created account details and HTTP 201 status
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param accountId The ID of the account to retrieve
     * @return ResponseEntity containing the account details and HTTP 200 status
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable @Min(value = 1, message = "Account ID must be greater than 0") Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    /**
     * Retrieves all bank accounts in the system.
     *
     * @return ResponseEntity containing a list of all accounts and HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param accountId The ID of the account to deposit into
     * @param request   The request containing the deposit amount
     * @return ResponseEntity containing the updated account details and HTTP 200 status
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     * @throws jakarta.validation.ConstraintViolationException     if request validation fails
     */
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable @Min(value = 1, message = "Account ID must be greater than 0") Long accountId,
            @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(accountService.deposit(accountId, request));
    }

    /**
     * Withdraws the specified amount from the account.
     *
     * @param accountId The ID of the account to withdraw from
     * @param request   The request containing the withdrawal amount
     * @return ResponseEntity containing the updated account details and HTTP 200 status
     * @throws com.bank.manager.exception.AccountNotFoundException     if no account is found with the given ID
     * @throws com.bank.manager.exception.InsufficientBalanceException if the account has insufficient funds
     * @throws jakarta.validation.ConstraintViolationException         if request validation fails
     */
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable @Min(value = 1, message = "Account ID must be greater than 0") Long accountId,
            @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(accountService.withdraw(accountId, request));
    }

    /**
     * Transfers money between two accounts.
     *
     * @param request The transfer request containing source account, destination account, and amount
     * @return ResponseEntity containing both updated account details and HTTP 200 status
     * @throws com.bank.manager.exception.AccountNotFoundException     if either account is not found
     * @throws com.bank.manager.exception.InsufficientBalanceException if the source account has insufficient funds
     * @throws IllegalArgumentException                                if source and destination accounts are the same
     * @throws jakarta.validation.ConstraintViolationException         if request validation fails
     */
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(accountService.transfer(request));
    }

    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId The ID of the account to get transactions for
     * @return ResponseEntity containing a list of transactions and HTTP 200 status
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     */
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @PathVariable @Min(value = 1, message = "Account ID must be greater than 0") Long accountId) {
        return ResponseEntity.ok(accountService.getTransactionsForAccount(accountId));
    }

}