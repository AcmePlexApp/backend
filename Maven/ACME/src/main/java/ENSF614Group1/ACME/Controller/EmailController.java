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

import ENSF614Group1.ACME.Model.Email;
import ENSF614Group1.ACME.Service.EmailService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping
	public ResponseEntity<Email> createEmail(@RequestBody Email email){
		Email createdEmail = emailService.createEmail(email);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEmail);	
	}
	
	@GetMapping
	public ResponseEntity<List<Email>> getAllEmails() {
		return ResponseEntity.status(HttpStatus.OK).body(emailService.getAllEmails());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Email> getEmailById(@PathVariable Long id) {
		Email email = emailService.getEmailById(id);
		return ResponseEntity.status(HttpStatus.OK).body(email);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Email> updateEmailById(@PathVariable Long id, @RequestBody Email emailDetails){
		Email email = emailService.updateEmail(id, emailDetails);
		return ResponseEntity.status(HttpStatus.OK).body(email);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmailById(@PathVariable Long id){
		emailService.deleteEmail(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
