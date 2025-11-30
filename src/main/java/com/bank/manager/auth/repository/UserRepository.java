package com.bank.manager.auth.repository;

import com.bank.manager.auth.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    List<User> findAll();

}