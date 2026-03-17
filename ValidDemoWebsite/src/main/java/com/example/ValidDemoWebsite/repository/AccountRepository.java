package com.example.ValidDemoWebsite.repository;

import com.example.ValidDemoWebsite.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByLoginName(String loginName);
}
