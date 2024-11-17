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

import ENSF614Group1.ACME.Model.Payment;
import ENSF614Group1.ACME.Service.PaymentService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<Payment> createPayment(@RequestBody Payment payment){
		Payment createdPayment = paymentService.createPayment(payment);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);	
	}
	
	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayments() {
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.getAllPayments());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
		Payment payment = paymentService.getPaymentById(id);
		return ResponseEntity.status(HttpStatus.OK).body(payment);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Payment> updatePaymentById(@PathVariable Long id, @RequestBody Payment paymentDetails){
		Payment payment = paymentService.updatePayment(id, paymentDetails);
		return ResponseEntity.status(HttpStatus.OK).body(payment);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePaymentById(@PathVariable Long id){
		paymentService.deletePayment(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
