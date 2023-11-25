package com.groupfour.sps.repositories.listing;


import com.groupfour.sps.models.listing.attributes.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> { }
