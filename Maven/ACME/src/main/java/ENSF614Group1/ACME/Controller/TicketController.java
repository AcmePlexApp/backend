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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ENSF614Group1.ACME.Model.Ticket;
import ENSF614Group1.ACME.Service.TicketService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket){
		Ticket createdTicket = ticketService.createTicket(ticket);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);	
	}
	
	@GetMapping
	public ResponseEntity<List<Ticket>> getAllTickets() {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.getAllTickets());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
		Ticket ticket = ticketService.getTicketById(id);
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Ticket> updateTicketById(@PathVariable Long id, @RequestBody Ticket ticketDetails){
		Ticket ticket = ticketService.updateTicket(id, ticketDetails);
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTicketById(@PathVariable Long id){
		ticketService.deleteTicket(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
