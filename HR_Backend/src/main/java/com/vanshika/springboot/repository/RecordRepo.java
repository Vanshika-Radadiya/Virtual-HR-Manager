package com.vanshika.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.Record;
import com.vanshika.springboot.model.User;
import java.util.List;

@Repository
public interface RecordRepo extends JpaRepository<Record, Long>{
	List<Record> findByInterviewIdAndUserIdOrderByRecordIdAsc(int interviewId, int userId);
}
