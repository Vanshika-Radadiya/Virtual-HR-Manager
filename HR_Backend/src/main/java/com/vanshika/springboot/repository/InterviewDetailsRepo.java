package com.vanshika.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.CompId;
import com.vanshika.springboot.model.InterviewDetails;

@Repository
public interface InterviewDetailsRepo extends JpaRepository<InterviewDetails, CompId> {
    boolean existsByIdInterviewIdAndIdUserId(int interviewId, int userId);
}

