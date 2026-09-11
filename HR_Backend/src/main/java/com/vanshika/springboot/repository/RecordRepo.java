package com.vanshika.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanshika.springboot.model.Record;
import com.vanshika.springboot.model.User;

@Repository
public interface RecordRepo extends JpaRepository<Record, Long>{
}
