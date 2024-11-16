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

import ENSF614Group1.ACME.Model.Credit;
import ENSF614Group1.ACME.Service.CreditService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/credit")
public class CreditController {
	
	@Autowired
	private CreditService creditService;
	
	@PostMapping
	public ResponseEntity<Credit> createCredit(@RequestBody Credit credit){
		Credit createdCredit = creditService.createCredit(credit);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCredit);	
	}
	
	@GetMapping
	public ResponseEntity<List<Credit>> getAllCredits() {
		return ResponseEntity.status(HttpStatus.OK).body(creditService.getAllCredits());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Credit> getCreditById(@PathVariable Long id) {
		Credit credit = creditService.getCreditById(id);
		return ResponseEntity.status(HttpStatus.OK).body(credit);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Credit> updateCreditById(@PathVariable Long id, @RequestBody Credit creditDetails){
		Credit credit = creditService.updateCredit(id, creditDetails);
		return ResponseEntity.status(HttpStatus.OK).body(credit);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCreditById(@PathVariable Long id){
		creditService.deleteCredit(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
