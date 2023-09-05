package com.groupfour.snb.security.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, UUID> {
    @Transactional
    @Modifying
    int updateConfirmedAt(String tokenId, LocalDateTime now);
}
