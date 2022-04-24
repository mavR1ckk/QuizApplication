package com.exam.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.models.User;
import com.exam.models.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	// Creating user
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {

		User local = this.userRepository.findByUsername(user.getUsername());
		
		System.out.println(user);

		// To check user is already present in database
		if (local != null) {
			System.out.println("User is already present");
			throw new Exception("User is already present");
		} else {

			// to store the role if not available in database
			userRoles.forEach(role -> {
				roleRepository.save(role.getRole());
			});

			// To store the user roles in user
			user.getUserRoles().addAll(userRoles);

			// Save user in database
			local = this.userRepository.save(user);

		}
		return local;
	}

	@Override
	public User getUser(String userName) {
		return this.userRepository.findByUsername(userName);
	}

	@Override
	public void deleteuser(long id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public ResponseEntity<User> updateUser(User user) {

		Optional<User> optional = userRepository.findById(user.getId());

		User currentUSer = optional.get();
		
		if(currentUSer==null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		currentUSer.setEmail(user.getEmail());
		currentUSer.setFirstName(user.getFirstName());
		currentUSer.setLastName(user.getLastName());
		currentUSer.setPassword(user.getPassword());
		currentUSer.setPhone(user.getPhone());
		currentUSer.setProfile(user.getProfile());
		currentUSer.setUsername(user.getUsername());
		
		userRepository.save(currentUSer);

		return new ResponseEntity<User>(currentUSer,HttpStatus.OK);
	}

}
