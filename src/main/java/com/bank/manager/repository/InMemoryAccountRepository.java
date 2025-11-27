package com.bank.manager.repository;

import com.bank.manager.model.Account;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of AccountRepository using a ConcurrentHashMap.
 */
@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<Long, Account> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Saves the given account to the repository.
     * If the account has no ID, a new one will be generated.
     *
     * @param account The account to save
     * @return The saved account (with generated ID if new)
     */
    @Override
    public Account save(Account account) {
        if (account.getAccountId() == null) {
            account.setAccountId(idGenerator.getAndIncrement());
        }
        storage.put(account.getAccountId(), account);
        return account;
    }

    /**
     * Finds an account by its unique identifier.
     *
     * @param accountId The ID of the account to find
     * @return An Optional containing the account if found, or empty if not found
     */
    @Override
    public Optional<Account> findById(Long accountId) {
        return Optional.ofNullable(storage.get(accountId));
    }

    /**
     * Retrieves all accounts in the repository.
     *
     * @return A list of all accounts (may be empty if no accounts exist)
     */
    @Override
    public List<Account> findAll() {
        return new ArrayList<>(storage.values());
    }
    
}