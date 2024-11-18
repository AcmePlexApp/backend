package ENSF614Group1.ACME.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.RegisteredUser;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Service.UserService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);	
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User userDetails){
		User user = userService.updateUser(id, userDetails);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping("/{userId}/credits/{amount}")
	public ResponseEntity<String> addCreditToUser(@PathVariable Long userId, @PathVariable Double amount){
		User user = userService.addCreditToUser(userId, amount);
		//Credit credit = creditService.getMovieById(movieId);
		String response = amount + " credit has been added to " + user.getUsername() + " account.";
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/{userId}/register/{creditCardId}")
	public RegisteredUser register(@PathVariable Long userId, @PathVariable Long creditCardId){
		return userService.register(userId, creditCardId);
	}
	
	@PostMapping("/{userId}/applycredits/{amount}")
	public ResponseEntity<String> applyCredits(@PathVariable Long userId, @PathVariable Double amount){
		Double remaining = userService.applyCredits(userId, amount);
		String response = (amount - remaining) + " credit has been applied to the requested amount. " + remaining + " of requested amount remaining.";
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{userId}/registered")
	public boolean getRegisteredByID(@PathVariable Long userId) {
		return userService.isUserRegistered(userId);
	}
	
}
