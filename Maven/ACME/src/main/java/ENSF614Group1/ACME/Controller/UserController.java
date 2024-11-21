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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.RegisteredUser;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Security.JWTUtil;
import ENSF614Group1.ACME.Service.UserService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired private UserService userService;
	
    @Autowired private JWTUtil jwtUtil;
	
	@GetMapping
	public ResponseEntity<User> getUser(@RequestHeader("Authorization") String authHeader) {
		try {
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String authHeader){
		try {
			String token = jwtUtil.getAuthToken(authHeader);
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			userService.deleteUser(user.getID());
			jwtUtil.blacklistToken(token);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}	
	
	@PostMapping("/addcredit/{amount}")
	public ResponseEntity<String> addCreditToUser(@RequestHeader("Authorization") String authHeader, @PathVariable Double amount){
		try {
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			user = userService.addCreditToUser(user.getID(), amount);
			String response = amount + " credit has been added to " + user.getUsername() + "'s account.";
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/register/{creditCardId}")
	public ResponseEntity<RegisteredUser> register(@RequestHeader("Authorization") String authHeader, @PathVariable Long creditCardId){
		try {
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			RegisteredUser registeredUser = userService.register(user.getID(), creditCardId);
			return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/applycredits/{amount}")
	public ResponseEntity<String> applyCredits(@RequestHeader("Authorization") String authHeader, @PathVariable Double amount){
		try {
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			Double remaining = userService.applyCredits(user.getID(), amount);
			String response = (amount - remaining) + " credit has been applied to the requested amount. " + remaining + " of requested amount remaining.";
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}	
}
