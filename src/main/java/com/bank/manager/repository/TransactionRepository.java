package com.bank.manager.repository;

import com.bank.manager.model.Transaction;

import java.util.List;

/**
 * Repository abstraction for storing and retrieving transactions.
 */
public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(Long accountId);
    
}