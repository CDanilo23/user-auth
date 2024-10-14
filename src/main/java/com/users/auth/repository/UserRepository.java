package com.users.auth.repository;

import com.users.auth.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, UUID> {
    @Query(value = "select ul from UserLogin ul where ul.email = :email")
    Optional<UserLogin> existsByEmail(@Param("email") String email);
}
