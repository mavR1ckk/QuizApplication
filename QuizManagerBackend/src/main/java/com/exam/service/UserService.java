package com.exam.service;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.exam.models.User;
import com.exam.models.UserRole;

public interface UserService {

	// creating user
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;

	// get user
	public User getUser(String username);
	
	// delete user
	public void deleteuser(long id);
	
	// update user
	public ResponseEntity<User> updateUser(User user);
}
