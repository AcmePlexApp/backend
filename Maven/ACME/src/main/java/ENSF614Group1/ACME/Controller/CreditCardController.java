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

import ENSF614Group1.ACME.Model.CreditCard;
import ENSF614Group1.ACME.Service.CreditCardService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/creditCard")
public class CreditCardController {
	
	@Autowired
	private CreditCardService creditCardService;
	
	@PostMapping
	public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard){
		CreditCard createdCreditCard = creditCardService.createCreditCard(creditCard);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCreditCard);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
		CreditCard creditCard = creditCardService.getCreditCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(creditCard);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCreditCardById(@PathVariable Long id){
		creditCardService.deleteCreditCard(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
