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

import ENSF614Group1.ACME.Model.Bank;
import ENSF614Group1.ACME.Service.BankService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	@PostMapping
	public ResponseEntity<Bank> createBank(@RequestBody Bank bank){
		Bank createdBank = bankService.createBank(bank);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBank);	
	}
	
	@GetMapping
	public ResponseEntity<List<Bank>> getAllBanks() {
		return ResponseEntity.status(HttpStatus.OK).body(bankService.getAllBanks());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBankById(@PathVariable Long id){
		bankService.deleteBank(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
