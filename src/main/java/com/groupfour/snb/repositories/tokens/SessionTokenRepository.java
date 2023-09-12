package com.groupfour.snb.repositories.tokens;

import com.groupfour.snb.models.tokens.SessionToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionTokenRepository extends CrudRepository<SessionToken, String> {
    Optional<SessionToken> findByUserId(String userId);
}
