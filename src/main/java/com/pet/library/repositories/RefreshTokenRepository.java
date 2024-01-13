package com.pet.library.repositories;

import com.pet.library.entities.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken e WHERE e.expiryDate < CURRENT_TIMESTAMP")
    void deleteExpired();
}
