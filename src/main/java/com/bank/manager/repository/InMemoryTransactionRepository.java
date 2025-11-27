package com.bank.manager.repository;

import com.bank.manager.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of TransactionRepository.
 */
@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<Long, List<Transaction>> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction.getTransactionId() == null) {
            transaction.setTransactionId(idGenerator.getAndIncrement());
        }
        storage.computeIfAbsent(transaction.getAccountId(), k -> new ArrayList<>())
                .add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return storage.getOrDefault(accountId, Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed()) // latest first
                .collect(Collectors.toList());
    }

}