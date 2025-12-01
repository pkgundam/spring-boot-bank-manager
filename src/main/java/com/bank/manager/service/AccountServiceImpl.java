package com.bank.manager.service;

import com.bank.manager.auth.repository.UserRepository;
import com.bank.manager.dto.*;
import com.bank.manager.entity.AccountEntity;
import com.bank.manager.entity.TransactionEntity;
import com.bank.manager.auth.entity.UserEntity;
import com.bank.manager.entity.enums.TransactionType;
import com.bank.manager.exception.AccountNotFoundException;
import com.bank.manager.exception.ForbiddenOperationException;
import com.bank.manager.exception.InsufficientBalanceException;
import com.bank.manager.repository.AccountRepository;
import com.bank.manager.repository.TransactionRepository;
import com.bank.manager.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Default implementation of AccountService using an in-memory repository.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // --------------------------------------------------------
    // CREATE ACCOUNT
    // --------------------------------------------------------
    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        UserEntity owner = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal initialBalance = request.getInitialBalance() == null
                ? BigDecimal.ZERO
                : request.getInitialBalance();
        AccountEntity account = new AccountEntity(
                request.getAccountName(),
                initialBalance,
                owner
        );
        AccountEntity saved = accountRepository.save(account);

        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            recordTransaction(saved, TransactionType.DEPOSIT, initialBalance, null,
                    "Initial deposit on account creation");
        }
        return toResponse(saved);
    }

    // --------------------------------------------------------
    // GET ACCOUNT BY ID
    // --------------------------------------------------------
    @Override
    public AccountResponse getAccountById(Long accountId) {
        AccountEntity account = getAccessibleAccount(accountId);
        return toResponse(account);
    }

    // --------------------------------------------------------
    // GET ALL ACCOUNTS FOR CURRENT USER
    // --------------------------------------------------------
    @Override
    public List<AccountResponse> getAllAccounts() {
        Long userId = SecurityUtil.getCurrentUserId();
        return accountRepository.findByOwnerId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // --------------------------------------------------------
    // DEPOSIT
    // --------------------------------------------------------
    @Override
    public AccountResponse deposit(Long accountId, AmountRequest request) {
        AccountEntity account = getAccessibleAccount(accountId);
        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        recordTransaction(
                account,
                TransactionType.DEPOSIT,
                request.getAmount(),
                null,
                "Deposit"
        );
        return toResponse(account);
    }

    // --------------------------------------------------------
    // WITHDRAW
    // --------------------------------------------------------
    @Override
    public AccountResponse withdraw(Long accountId, AmountRequest request) {
        AccountEntity account = getAccessibleAccount(accountId);
        BigDecimal amount = request.getAmount();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(account.getBalance(), amount);
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        recordTransaction(
                account,
                TransactionType.WITHDRAWAL,
                amount,
                null,
                "Withdrawal"
        );
        return toResponse(account);
    }

    // --------------------------------------------------------
    // TRANSFER
    // --------------------------------------------------------
    @Override
    public TransferResponse transfer(TransferRequest request) {
        AccountEntity from = getAccessibleAccount(request.getFromAccountId()); // must be owned or admin
        AccountEntity to = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getToAccountId()));
        BigDecimal amount = request.getAmount();
        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(from.getBalance(), amount);
        }

        // UPDATE BALANCES
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // RECORD TX #1 – outgoing
        recordTransaction(
                from,
                TransactionType.TRANSFER_OUT,
                amount,
                to.getAccountId(),
                "Transfer to account " + to.getAccountId()
        );

        // RECORD TX #2 – incoming
        recordTransaction(
                to,
                TransactionType.TRANSFER_IN,
                amount,
                from.getAccountId(),
                "Transfer from account " + from.getAccountId()
        );
        return new TransferResponse(toResponse(from), toResponse(to));
    }

    // --------------------------------------------------------
    // GET TRANSACTIONS FOR ACCOUNT
    // --------------------------------------------------------
    @Override
    public List<TransactionResponse> getTransactionsForAccount(Long accountId) {
        getAccessibleAccount(accountId); // ensures access
        return transactionRepository.findByAccountAccountId(accountId)
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }

    // --------------------------------------------------------
    // PRIVATE HELPERS
    // --------------------------------------------------------

    private AccountEntity getAccessibleAccount(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        // ADMIN can see all
        if (SecurityUtil.isAdmin()) return account;

        Long currentUser = SecurityUtil.getCurrentUserId();
        Long ownerId = account.getOwner().getId();
        if (!ownerId.equals(currentUser)) {
            throw new ForbiddenOperationException("You do not own this account");
        }
        return account;
    }

    private void recordTransaction(AccountEntity account,
                                   TransactionType type,
                                   BigDecimal amount,
                                   Long relatedAccountId,
                                   String description) {
        TransactionEntity tx = new TransactionEntity(
                type,
                amount,
                account.getBalance(), // balance after operation
                relatedAccountId,
                description
        );
        tx.setAccount(account); // set relationship
        transactionRepository.save(tx);
    }

    private AccountResponse toResponse(AccountEntity account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getAccountName(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }

}