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

import ENSF614Group1.ACME.Model.RegisteredUser;
import ENSF614Group1.ACME.Service.RegisteredUserService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/registeredUser")
public class RegisteredUserController {
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@PostMapping
	public ResponseEntity<RegisteredUser> createRegisteredUser(@RequestBody RegisteredUser registeredUser){
		RegisteredUser createdRegisteredUser = registeredUserService.createRegisteredUser(registeredUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRegisteredUser);	
	}
	
	@GetMapping
	public ResponseEntity<List<RegisteredUser>> getAllRegisteredUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(registeredUserService.getAllRegisteredUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegisteredUser> getRegisteredUserById(@PathVariable Long id) {
		RegisteredUser registeredUser = registeredUserService.getRegisteredUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RegisteredUser> updateRegisteredUserById(@PathVariable Long id, @RequestBody RegisteredUser registeredUserDetails){
		RegisteredUser registeredUser = registeredUserService.updateRegisteredUser(id, registeredUserDetails);
		return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRegisteredUserById(@PathVariable Long id){
		registeredUserService.deleteRegisteredUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
