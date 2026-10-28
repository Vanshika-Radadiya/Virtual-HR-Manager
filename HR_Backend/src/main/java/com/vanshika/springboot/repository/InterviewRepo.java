package com.vanshika.springboot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.User;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Integer>{
	@Query(value="select i.interview_id, i.role, i.description, i.start_date, i_d.end_date, i.recruiter_id from interview i inner join interview_details i_d ON i.interview_id = i_d.interview_id where i_d.user_id=:UID;", nativeQuery=true)
	List<Interview> getInterviewByUserId(int UID);

	Interview getRoleByInterviewId(int interviewId);
	List<Interview> findByRecruiterIdOrderByStartDateDesc(Integer recruiterId);
}
