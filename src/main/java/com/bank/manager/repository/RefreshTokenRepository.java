package com.bank.manager.repository;

import com.bank.manager.entity.RefreshTokenEntity;
import com.bank.manager.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    @Query("select t from RefreshTokenEntity t where t.user.id = :userId and t.revoked = false")
    List<RefreshTokenEntity> findActiveTokens(Long userId);

}