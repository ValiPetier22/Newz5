package com.stackroute.user.controller;

import com.stackroute.user.model.User;
import com.stackroute.user.service.UserAuthService;
import com.stackroute.user.util.exception.UserAlreadyExistsException;
import com.stackroute.user.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    /*
	 * Autowiring should be implemented for the UserAuthService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */

	@Autowired
	UserAuthService userAuthService;
    public UserAuthController(UserAuthService userAuthService) {
	}

    /*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user, 
	 * UserAlreadyExistsException is caught.
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP POST method
	 */

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody User user){
		try{
			userAuthService.saveUser(user);
			return new ResponseEntity<>("User saved",HttpStatus.CREATED);
		}catch (UserAlreadyExistsException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}

	/* 
	 * Define a handler method which will authenticate a user by reading the Serialized user
	 * object from request body containing the username and password. The username and password should be validated 
	 * before proceeding ahead with JWT token generation. The user credentials will be validated against the database entries. 
	 * The error should be return if validation is not successful. If credentials are validated successfully, then JWT
	 * token will be generated. The token should be returned back to the caller along with the API response.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP POST method
	*/

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user){
		try {
			return new ResponseEntity<>
					(userAuthService.findByUserIdAndPassword(user.getUserId(), user.getPassword()),HttpStatus.OK);
		}catch (UserNotFoundException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
	}
    


}
