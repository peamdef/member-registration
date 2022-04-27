package com.test.registation.repository;

import com.test.registation.entity.User;
import com.test.registation.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByReferenceCode(String referenceCode);
}
