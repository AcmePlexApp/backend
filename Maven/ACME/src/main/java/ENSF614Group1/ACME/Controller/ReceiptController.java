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

import ENSF614Group1.ACME.Model.Receipt;
import ENSF614Group1.ACME.Service.ReceiptService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
	
	@Autowired
	private ReceiptService receiptService;
	
	@PostMapping
	public ResponseEntity<Receipt> createReceipt(@RequestBody Receipt receipt){
		Receipt createdReceipt = receiptService.createReceipt(receipt);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdReceipt);	
	}
	
	@GetMapping
	public ResponseEntity<List<Receipt>> getAllReceipts() {
		return ResponseEntity.status(HttpStatus.OK).body(receiptService.getAllReceipts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Receipt> getReceiptById(@PathVariable Long id) {
		Receipt receipt = receiptService.getReceiptById(id);
		return ResponseEntity.status(HttpStatus.OK).body(receipt);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Receipt> updateReceiptById(@PathVariable Long id, @RequestBody Receipt receiptDetails){
		Receipt receipt = receiptService.updateReceipt(id, receiptDetails);
		return ResponseEntity.status(HttpStatus.OK).body(receipt);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReceiptById(@PathVariable Long id){
		receiptService.deleteReceipt(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
