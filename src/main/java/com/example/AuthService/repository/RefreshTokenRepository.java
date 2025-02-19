package com.example.AuthService.repository;

import com.example.AuthService.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken , Long> {

    Optional<RefreshToken> findByToken(String token);
}

