package com.groupfour.sps.repositories;


import com.groupfour.sps.models.listing.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> { }
