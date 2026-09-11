package com.vanshika.springboot.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.CompId;
import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.Result;
import com.vanshika.springboot.model.User;

@Repository
public interface ResultRepo extends JpaRepository<Result, CompId>{
	Result getByCompId(CompId compId);
	
	@Query(value="Select * from result where user_id =:UID ", nativeQuery=true)
	List<Result> getResultByUserId(int UID);
}
