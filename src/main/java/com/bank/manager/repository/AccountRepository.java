package com.bank.manager.repository;

import com.bank.manager.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findByOwnerId(Long ownerId);

}