package com.bank.manager.repository;

import com.bank.manager.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Repository abstraction for storing and retrieving accounts.
 */
public interface AccountRepository {

    /**
     * Saves the given account to the repository.
     * If the account has no ID, a new one will be generated.
     *
     * @param account The account to save
     * @return The saved account (with generated ID if new)
     */
    Account save(Account account);

    /**
     * Finds an account by its unique identifier.
     *
     * @param accountId The ID of the account to find
     * @return An Optional containing the account if found, or empty if not found
     */
    Optional<Account> findById(Long accountId);

    /**
     * Retrieves all accounts in the repository.
     *
     * @return A list of all accounts (may be empty if no accounts exist)
     */
    List<Account> findAll();

}