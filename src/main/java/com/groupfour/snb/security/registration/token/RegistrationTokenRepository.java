package com.groupfour.snb.security.registration.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, UUID> { }
