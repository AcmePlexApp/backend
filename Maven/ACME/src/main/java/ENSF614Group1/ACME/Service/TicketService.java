package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ENSF614Group1.ACME.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ENSF614Group1.ACME.Model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

	@Autowired 
	private TicketRepository ticketRepository;
	
	@Autowired 
	private UserRepository userRepository;

	@Transactional
	public Ticket createTicket(Ticket ticket) {
		Optional<User> optUser = userRepository.findById(ticket.getUser().getID());
		if(optUser.isEmpty()) {
			throw new EntityNotFoundException("User does not exist.");
		}
		User user = optUser.get();
		ticket.setUser(user);
		
		return ticketRepository.save(ticket);
	}
	
	public List<Ticket> getAllTickets(){
		return ticketRepository.findAll();
	}
	
	public Ticket getTicketById(Long id){
		Optional<Ticket> ticket = ticketRepository.findById(id);
		if(ticket.isEmpty()) {
			throw new EntityNotFoundException("Ticket does not exist.");
		}
		return ticket.get();
	}
	
	@Transactional
	public Ticket updateTicket(Long id, Ticket ticketDetails) {
		Optional<Ticket> ticket = ticketRepository.findById(id);
		if (ticket.isEmpty()) {
			throw new EntityNotFoundException("Ticket does not exist.");
		}
		Ticket s = ticket.get();
		s.setUser(ticketDetails.getUser());
		return ticketRepository.save(s);
	}
	
	@Transactional
	public void deleteTicket(Long id) {
		if (!ticketRepository.existsById(id)) {
			throw new EntityNotFoundException("Ticket does not exist.");
		}
		ticketRepository.deleteById(id);
	}
    
}