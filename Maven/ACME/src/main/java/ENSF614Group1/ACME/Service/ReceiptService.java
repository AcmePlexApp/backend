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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {

	@Autowired private ReceiptRepository receiptRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private CreditCardRepository creditCardRepository;

	
	@Transactional
	public Receipt createReceipt(Receipt receipt) {
		return receiptRepository.save(receipt);
	}
	
	public List<Receipt> getAllReceipts(){
		return receiptRepository.findAll();
	}
	
	public Receipt getReceiptById(Long id){
		Optional<Receipt> receipt = receiptRepository.findById(id);
		if(receipt.isEmpty()) {
			throw new EntityNotFoundException("Receipt does not exist.");
		}
		return receipt.get();
	}

	
	@Transactional
	public Receipt updateReceipt(Long id, Receipt receiptDetails) {
		Optional<Receipt> receipt = receiptRepository.findById(id);
		if (receipt.isEmpty()) {
			throw new EntityNotFoundException("Receipt does not exist.");
		}
		Receipt s = receipt.get();
		s.setTitle(receiptDetails.getTitle());
		return receiptRepository.save(s);
	}
	
	@Transactional
	public void deleteReceipt(Long id) {
		if (!receiptRepository.existsById(id)) {
			throw new EntityNotFoundException("Receipt does not exist.");
		}
		receiptRepository.deleteById(id);
	}
	
//	@Transactional
//	public void createPurchaseReceipt(User user, CreditCard creditCard, List<Ticket> tickets) {
//		Optional<User> optUser = userRepository.findById(user.getID());
//		if (optUser.isEmpty()) {
//			throw new EntityNotFoundException("User does not exist.");
//		}
//		User forUser = optUser.get();
//		
//		Optional<CreditCard> optCreditCard = creditCardRepository.findById(creditCard.getID());
//		if (optCreditCard.isEmpty()) {
//			throw new EntityNotFoundException("Credit Card does not exist.");
//		}
//		CreditCard withCreditCard = optCreditCard.get();
//		
//		List<Ticket> forTickets = new ArrayList<>();
//		for (Ticket ticket : payment.getTickets()) {
//			Optional<Ticket> optTicket = ticketRepository.findById(ticket.getID());
//			if(optTicket.isEmpty()) {
//				throw new EntityNotFoundException("Cannot Create Purchase Receipt.  Ticket does not exist.");
//			}
//			Ticket tic = optTicket.get();
//			forTickets.add(tic);
//		}
//		
//	}
    
}