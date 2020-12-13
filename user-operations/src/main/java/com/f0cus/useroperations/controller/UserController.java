package com.f0cus.useroperations.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.f0cus.useroperations.entity.User;
import com.f0cus.useroperations.exception.UserNotFoundException;
import com.f0cus.useroperations.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users/test")
	public User testUserController() {
		//return "user controller is good to go!";
		return new User(1,"Test User",LocalDate.now());
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		throw new UserNotFoundException("User with id: "+ id + " does not exist");
		//return null;
	}
	
	@PostMapping("/users/create")
	public String createUser(@RequestBody User userInput) {
		User newUser = userRepository.save(userInput);
		if(newUser != null && newUser.getId() > 0) {
			return "User created, Id is : "+ newUser.getId();
		}
		return "User creation failed";
	}
	
	@PostMapping("/users/createWithResponseEntity")
	public ResponseEntity<User> createUserReturnResponseEntity(@Valid @RequestBody User userInput) {
		User newUser = userRepository.save(userInput);
		if(newUser != null && newUser.getId() > 0) {
			URI newUri = ServletUriComponentsBuilder
							.fromPath("http:////localhost:8080/users/{id}")
							.buildAndExpand(newUser.getId()).toUri();
			return ResponseEntity.created(newUri).build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id){
		Optional<User> deleteUser = userRepository.findById(id);
		if(deleteUser.isPresent()) {
			userRepository.deleteById(id);
			return new ResponseEntity<User>(deleteUser.get(),HttpStatus.OK);
		}
		throw new UserNotFoundException("User with id: "+ id + " does not exist");
	}
}
