package com.bank.manager.repository;

import com.bank.manager.model.Transaction;

import java.util.List;

/**
 * Repository abstraction for storing and retrieving transactions.
 */
public interface TransactionRepository {

    /**
     * Saves the given transaction to the repository.
     * If the transaction has no ID, a new one will be generated.
     *
     * @param transaction The transaction to save
     * @return The saved transaction (with generated ID if new)
     */
    Transaction save(Transaction transaction);

    /**
     * Finds all transactions for a specific account, sorted by creation time in descending order (latest first).
     *
     * @param accountId The ID of the account to find transactions for
     * @return A list of transactions for the given account, or an empty list if no transactions are found
     */
    List<Transaction> findByAccountId(Long accountId);
    
}