package com.groupfour.snb.repositories;

import com.groupfour.snb.models.tokens.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, String> {
    Optional<RegistrationToken> findByUserId(String userId);

}
