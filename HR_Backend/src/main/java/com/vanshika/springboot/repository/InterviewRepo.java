package com.vanshika.springboot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.User;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Integer>{
	@Query(value="Select * from interview where interview_id IN(select interview_id from interview_details where user_id =:UID)", nativeQuery=true)
	List<Interview> getInterviewByUserId(int UID);
	
	Interview getRoleByInterviewId(int interviewId);
}
