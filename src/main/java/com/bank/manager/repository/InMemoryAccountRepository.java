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

    @Override
    public Account save(Account account) {
        if (account.getAccountId() == null) {
            account.setAccountId(idGenerator.getAndIncrement());
        }
        storage.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return Optional.ofNullable(storage.get(accountId));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(storage.values());
    }
    
}