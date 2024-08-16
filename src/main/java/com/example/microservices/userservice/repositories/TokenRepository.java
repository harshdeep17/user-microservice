package com.example.microservices.userservice.repositories;

import com.example.microservices.userservice.models.Token;
import com.example.microservices.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Override
    <S extends Token> S save(S entity);
    Optional<Token> findByValueAndIsDeleted(String tokenValue, boolean isDeleted);
    Optional<Token> findByValueAndIsDeletedAndExpireAtGreaterThan(String tokenValue, boolean isDeleted, Date date);
}
