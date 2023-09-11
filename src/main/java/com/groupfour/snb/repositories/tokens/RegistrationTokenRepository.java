package com.groupfour.snb.repositories.tokens;

import com.groupfour.snb.models.tokens.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, String> {
}
