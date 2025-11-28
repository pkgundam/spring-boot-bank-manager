package com.bank.manager.service;

import com.bank.manager.dto.*;
import com.bank.manager.exception.AccountNotFoundException;
import com.bank.manager.exception.InsufficientBalanceException;
import com.bank.manager.model.Account;
import com.bank.manager.model.Transaction;
import com.bank.manager.model.TransactionType;
import com.bank.manager.repository.AccountRepository;
import com.bank.manager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation of AccountService using an in-memory repository.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Creates a new bank account with the provided details.
     *
     * @param request The request containing account creation details
     * @return The created account response
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {
        BigDecimal initialBalance = request.getInitialBalance() == null
                ? BigDecimal.ZERO
                : request.getInitialBalance();

        Account account = new Account(null,
                request.getHolderName(),
                initialBalance,
                LocalDateTime.now());

        Account saved = accountRepository.save(account);

        // Optional: record an initial transaction if initialBalance > 0
        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            recordTransaction(saved, TransactionType.DEPOSIT, initialBalance,
                    null, "Initial deposit on account creation");
        }

        return toResponse(saved);
    }

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param accountId The ID of the account to retrieve
     * @return The account details
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     */
    @Override
    public AccountResponse getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return toResponse(account);
    }

    /**
     * Retrieves all bank accounts in the system.
     *
     * @return A list of all account responses
     */
    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param accountId The ID of the account to deposit into
     * @param request The request containing the deposit amount
     * @return The updated account details
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     * @throws jakarta.validation.ConstraintViolationException if request validation fails
     */
    @Override
    public AccountResponse deposit(Long accountId, AmountRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        recordTransaction(account, TransactionType.DEPOSIT, request.getAmount(),
                null, "Deposit");

        return toResponse(account);
    }

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
    @Override
    public AccountResponse withdraw(Long accountId, AmountRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        BigDecimal amount = request.getAmount();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(account.getBalance(), amount);
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        recordTransaction(account, TransactionType.WITHDRAWAL, amount,
                null, "Withdrawal");

        return toResponse(account);
    }

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
    @Override
    public TransferResponse transfer(TransferRequest request) {
        Account from = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getFromAccountId()));
        Account to = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getToAccountId()));

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(from.getBalance(), request.getAmount());
        }

        // Update balances
        BigDecimal amount = request.getAmount();
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // Record two transactions: OUT for from, IN for to
        recordTransaction(from, TransactionType.TRANSFER_OUT, amount,
                to.getAccountId(), "Transfer to account " + to.getAccountId());
        recordTransaction(to, TransactionType.TRANSFER_IN, amount,
                from.getAccountId(), "Transfer from account " + from.getAccountId());

        return new TransferResponse(toResponse(from), toResponse(to));
    }

    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId The ID of the account to find transactions for
     * @return A list of transaction responses
     * @throws com.bank.manager.exception.AccountNotFoundException if no account is found with the given ID
     */
    @Override
    public List<TransactionResponse> getTransactionsForAccount(Long accountId) {
        // Ensure account exists (otherwise 404)
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(TransactionResponse::from)
                .collect(Collectors.toList());
    }

    // all private methods below
    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getHolderName(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }

    /**
     * Records a transaction in the database.
     *
     * @param account The account related to the transaction
     * @param type The type of the transaction
     * @param amount The amount of the transaction
     * @param relatedAccountId The ID of the related account in the transaction
     * @param description A human-readable description of the transaction
     */
    private void recordTransaction(Account account,
                                   TransactionType type,
                                   BigDecimal amount,
                                   Long relatedAccountId,
                                   String description) {
        Transaction tx = new Transaction(
                null,
                account.getAccountId(),
                type,
                amount,
                account.getBalance(),       // balance AFTER the operation
                LocalDateTime.now(),
                relatedAccountId,
                description
        );
        transactionRepository.save(tx);
    }

}