package com.vanshika.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.InterviewAssignment;

@Repository
public interface InterviewAssignmentRepo extends JpaRepository<InterviewAssignment, Long> {
	List<InterviewAssignment> findByInterviewIdOrderByUserIdAsc(int interviewId);
	boolean existsByInterviewIdAndUserId(int interviewId, int userId);
	Optional<InterviewAssignment> findByInterviewIdAndUserId(int interviewId, int userId);
}
