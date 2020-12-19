package com.f0cus.useroperations.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.f0cus.useroperations.exception.GenericException;
import com.f0cus.useroperations.exception.UserNotFoundException;
import com.f0cus.useroperations.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Operation(description="Test API Availability",
				summary="Checks if the API is running or not",
				method="GET",
				responses= {@ApiResponse(responseCode="200", content= {@Content(mediaType="application/json"),@Content(mediaType="application/xml")}),
							@ApiResponse(responseCode="404", content= {@Content(mediaType="application/json"),@Content(mediaType="application/json")})
					}
			)
	@GetMapping("/users/test")
	public User testUserController() {
		//return "user controller is good to go!";
		return new User(1,"Test User",LocalDate.now());
	}

	
	@Operation(description="Get All Users",
			summary="Fetch details of all users in the form of an array",
			method="GET",
			responses= {@ApiResponse(responseCode="200", 
									 content= {@Content(mediaType="application/json", 
									 					array=@ArraySchema(schema=@Schema(implementation=User.class))),
											   @Content(mediaType="application/xml",
													   array=@ArraySchema(schema=@Schema(implementation=User.class)))
											  }
									),
					@ApiResponse(responseCode="404", 
					 			 content= {@Content(mediaType="application/json", 
					 								schema=@Schema(implementation=GenericException.class)),
											@Content(mediaType="application/xml", 
						 							schema=@Schema(implementation=GenericException.class))
										  }
								)
						}
				)
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Operation(description="Get User Details",
			summary="Fetch details of a particular user",
			method="GET",
			responses= {@ApiResponse(responseCode="200", 
									 content= {@Content(mediaType="application/json", 
									 					schema=@Schema(implementation=User.class)),
											   @Content(mediaType="application/xml",
													   schema=@Schema(implementation=User.class))
											  }
									),
					@ApiResponse(responseCode="404", 
					 			 content= {@Content(mediaType="application/json", 
					 								schema=@Schema(implementation=GenericException.class)),
											@Content(mediaType="application/xml", 
						 							schema=@Schema(implementation=GenericException.class))
										  }
								)
						}
				)	
	@GetMapping("/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			EntityModel<User> resource = EntityModel.of(optionalUser.get());
			
			WebMvcLinkBuilder linkTo = 
					linkTo(methodOn(this.getClass()).getAllUsers());
			
			resource.add(linkTo.withRel("get-all-users"));
			return resource;
		}

		throw new UserNotFoundException(messageSource.getMessage("message.user_not_found_exception"
				, null, LocaleContextHolder.getLocale()) + id);
	}

	@Operation(description="Create User",
				summary="Create a new User in system",
				method="POST",
				responses= {@ApiResponse(responseCode="201"),
							@ApiResponse(responseCode="500", 
										content= {@Content(mediaType="application/json", 
					 								schema=@Schema(implementation=GenericException.class)),
												@Content(mediaType="application/xml", 
						 							schema=@Schema(implementation=GenericException.class))
												}
									)
							}
				)
	@PostMapping("/users")
	public ResponseEntity<User> createUserReturnResponseEntity(@Valid @RequestBody User userInput) {
		User newUser = userRepository.save(userInput);
		if(newUser != null && newUser.getId() > 0) {
			URI newUri = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(newUser.getId()).toUri();
			return ResponseEntity.created(newUri).build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Operation(description="Delete User",
			summary="Delete an existing User",
			method="DELETE",
			responses= {@ApiResponse(responseCode="200"),
						@ApiResponse(responseCode="500", 
									content= {@Content(mediaType="application/json", 
				 								schema=@Schema(implementation=GenericException.class)),
											@Content(mediaType="application/xml", 
					 							schema=@Schema(implementation=GenericException.class))
											}
								)
						}
			)
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id){
		Optional<User> deleteUser = userRepository.findById(id);
		if(deleteUser.isPresent()) {
			userRepository.deleteById(id);
			return new ResponseEntity<User>(deleteUser.get(),HttpStatus.OK);
		}
		throw new UserNotFoundException(messageSource.getMessage("message.user_not_found_exception"
				, null, LocaleContextHolder.getLocale()) + id);
	}
}
