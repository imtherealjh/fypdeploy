package com.uow.FYP_23_S1_11.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uow.FYP_23_S1_11.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUsername(String username);
}
