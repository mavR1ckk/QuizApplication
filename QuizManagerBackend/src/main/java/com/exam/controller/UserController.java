package com.exam.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.models.Role;
import com.exam.models.User;
import com.exam.models.UserRole;
import com.exam.service.UserService;

@RestController()
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	// To add new user
	@PostMapping("/")
	public ResponseEntity<Object> creaUser(@Valid @RequestBody User user, BindingResult result, Model model)
			throws Exception {

		if (result.hasErrors()) {
			
			System.out.println(result);
			
			Map<String, String> errors = new HashMap<>();
			result.getAllErrors().forEach(error->{
				
				String fieldName = ((FieldError) error).getField();
				String message = error.getDefaultMessage();
				
				errors.put(fieldName, message);
			});
			
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		} else {
			Set<UserRole> userRoles = new HashSet<>();

			Role role = new Role();
			role.setRoleId(45l);
			role.setRoleName("NORMAL");

			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);

			userRoles.add(userRole);

			User createUser = this.userService.createUser(user, userRoles);

			return new ResponseEntity<>(createUser, HttpStatus.OK);
		}

	}

	// To get the user
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {

		return userService.getUser(username);
	}

	// To delete the user
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		this.userService.deleteuser(id);
	}

	// to update user
	@PutMapping("/")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return this.userService.updateUser(user);
	}
}
