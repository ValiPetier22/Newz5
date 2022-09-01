package com.stackroute.userprofile.service;

import com.stackroute.userprofile.model.UserProfile;
import com.stackroute.userprofile.repository.UserProfileRepository;
import com.stackroute.userprofile.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.userprofile.util.exception.UserProfileNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class UserProfileServiceImpl implements UserProfileService {

	/*
	 * Autowiring should be implemented for the UserProfileRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private final UserProfileRepository userProfileRepository;
	public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
		this.userProfileRepository = userProfileRepository;
	}
	/*
	 * This method should be used to save a new userprofile.Call the corresponding method
	 * of Respository interface.
	 */

    public UserProfile registerUser(UserProfile user) throws UserProfileAlreadyExistsException {
		Optional<UserProfile> existUserProfile=userProfileRepository.findById(user.getUserId());
		if (existUserProfile.isPresent()){
			throw new UserProfileAlreadyExistsException("Already exists");

		}else {
			UserProfile userProfile= userProfileRepository.insert(user);
			if (userProfile!=null){
				return userProfile;
			}
			throw new UserProfileAlreadyExistsException("User profile is null");
		}
    }

	/*
	 * This method should be used to update a existing userprofile.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public UserProfile updateUser(String userId, UserProfile user) throws UserProfileNotFoundException {
		Optional<UserProfile> existUserProfile=userProfileRepository.findById(userId);
		if (existUserProfile.isEmpty()){
			throw new UserProfileNotFoundException("Not found user");
		}else {
			userProfileRepository.save(user);
		}
		return user;
    }

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public boolean deleteUser(String userId) throws UserProfileNotFoundException {
		Optional<UserProfile> existUser=userProfileRepository.findById(userId);
		boolean repsonse;
		if (existUser.isPresent()){
			userProfileRepository.deleteById(userId);
			repsonse=true;
			return true;
		}else {
			throw new UserProfileNotFoundException("Profile not found");
		}
    }
    
	/*
	 * This method should be used to get userprofile by userId.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public UserProfile getUserById(String userId) throws UserProfileNotFoundException {
    	Optional<UserProfile> existUser=userProfileRepository.findById(userId);
		if (existUser.isEmpty()){
			throw new UserProfileNotFoundException("User not found");
		}else {
			return existUser.get();
		}
    }
}
