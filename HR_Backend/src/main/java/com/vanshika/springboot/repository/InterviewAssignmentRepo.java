package com.vanshika.springboot.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.InterviewAssignment;

@Repository
public interface InterviewAssignmentRepo extends JpaRepository<InterviewAssignment, Long> {
	List<InterviewAssignment> findByInterviewIdOrderByUserIdAsc(int interviewId);
	List<InterviewAssignment> findByUserIdOrderByStartDateDesc(int userId);
	boolean existsByInterviewIdAndUserId(int interviewId, int userId);
	Optional<InterviewAssignment> findByInterviewIdAndUserId(int interviewId, int userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select assignment from InterviewAssignment assignment "
			+ "where assignment.interviewId = :interviewId and assignment.userId = :userId")
	Optional<InterviewAssignment> findByInterviewIdAndUserIdForUpdate(
			@Param("interviewId") int interviewId, @Param("userId") int userId);
}
