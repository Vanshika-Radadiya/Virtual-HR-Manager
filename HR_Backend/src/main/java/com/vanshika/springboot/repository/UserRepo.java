package com.vanshika.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vanshika.springboot.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	User getUserByUsernameAndPassword(String username, String password);
	User getUsernameByUserId(int userId);
}
