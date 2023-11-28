package com.groupfour.sps.repositories;

import com.groupfour.sps.models.tokens.TwoFactorToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TwoFactorRepository extends JpaRepository<TwoFactorToken, String> {
    Optional<TwoFactorToken> findByToken(String token);
}
