package com.bank.manager.repository;

import com.bank.manager.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Repository abstraction for storing and retrieving accounts.
 */
public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(Long accountId);

    List<Account> findAll();

}