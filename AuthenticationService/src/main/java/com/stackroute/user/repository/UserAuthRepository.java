package com.stackroute.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stackroute.user.model.User;
import org.springframework.stereotype.Repository;


/*
* This class is implementing the JpaRepository interface for Note.
* Annotate this class with @Repository annotation
* */

@Repository
public interface UserAuthRepository  extends JpaRepository<User,String>{
	public User findByUserIdAndPassword(String userId, String password);

}
