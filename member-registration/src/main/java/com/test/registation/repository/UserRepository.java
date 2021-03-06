package com.test.registation.repository;

import com.test.registation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
 
 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByReferenceCode(String referenceCode);
    List<User> findAllByUsernameOrEmailOrPhoneNumber(String username,String email,String phoneNumber);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
