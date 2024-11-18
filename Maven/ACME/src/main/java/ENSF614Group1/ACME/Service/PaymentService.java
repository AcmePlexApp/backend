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
public class PaymentService {

	@Autowired private PaymentRepository paymentRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private TicketRepository ticketRepository;
	@Autowired private ReceiptRepository receiptRepository;
	@Autowired private CreditCardRepository creditCardRepository;

	@Transactional
	public Payment createPayment(Payment payment) {
		Optional<User> optUser = userRepository.findById(payment.getUser().getID());
		if(optUser.isEmpty()) {
			throw new EntityNotFoundException("Cannot Create Payment.  User does not exist.");
		}
		User user = optUser.get();
		
		Optional<CreditCard> optCreditCard = creditCardRepository.findById(payment.getCreditCard().getID());
		if(optCreditCard.isEmpty()) {
			throw new EntityNotFoundException("Cannot Create Payment.  Credit Card does not exist.");
		}
		CreditCard creditCard = optCreditCard.get();

		
		List<Receipt> receipts = new ArrayList<>();
		for (Receipt receipt : payment.getReceipts()) {
			Optional<Receipt> optReceipt = receiptRepository.findById(receipt.getID());
			if(optReceipt.isEmpty()) {
				throw new EntityNotFoundException("Cannot Create Payment.  Receipt does not exist.");
			}
			Receipt rec = optReceipt.get();
			receipts.add(rec);
		}
		
		List<Ticket> tickets = new ArrayList<>();
		for (Ticket ticket : payment.getTickets()) {
			Optional<Ticket> optTicket = ticketRepository.findById(ticket.getID());
			if(optTicket.isEmpty()) {
				throw new EntityNotFoundException("Cannot Create Payment.  Ticket does not exist.");
			}
			Ticket tic = optTicket.get();
			tickets.add(tic);
		}
		
		
		payment.setUser(user);
		payment.setCreditCard(creditCard);
		payment.setTickets(tickets);
		Receipt receipt = createPurchaseReceipt(payment);
		List<Receipt> receiptList = new ArrayList<>();
		receiptList.add(receipt);
		payment.setReceipts(receiptList);
		return paymentRepository.save(payment);
	}
	
	public Receipt createPurchaseReceipt(Payment payment) {
		User user = payment.getUser();
		String emailAddress = user.getEmail();
		String username = user.getUsername();
		List<Ticket> tickets = payment.getTickets();
		CreditCard creditCard = payment.getCreditCard();
		String title = "Purchase Receipt for " + tickets.size() + " tickets for " + username;
		String body = "Paid on Credit Card #" + creditCard.getCardNumber() + "\n\n";
		for(Ticket ticket: tickets) {
			body.concat("Ticket#" + ticket.getID() + "\n");
			//Seat seat = ticket.getSeat();
			//Theater theatre = ticket.getTheater();
			//Showtime showtime = ticket.getShowtime();
			body.concat("Theater " + "TBD - Need to hook up in database" + "\n");
			body.concat("Showtime " + "TBD - Need to hook up in database" + "\n");
			body.concat("Seat " + "TBD - Need to hook up in database" + "\n");
			body.concat("TBD - need to actually send an email");
		}
		Receipt purchaseReceipt = new Receipt(title, body, LocalDateTime.now(), emailAddress);
		return receiptRepository.save(purchaseReceipt);
	}
	
	public List<Payment> getAllPayments(){
		return paymentRepository.findAll();
	}
	
	public Payment getPaymentById(Long id){
		Optional<Payment> payment = paymentRepository.findById(id);
		if(payment.isEmpty()) {
			throw new EntityNotFoundException("Payment does not exist.");
		}
		return payment.get();
	}

	
	@Transactional
	public Payment updatePayment(Long id, Payment paymentDetails) {
		Optional<Payment> payment = paymentRepository.findById(id);
		if (payment.isEmpty()) {
			throw new EntityNotFoundException("Payment does not exist.");
		}
		Payment s = payment.get();
		
		
		
		s.setAmount(paymentDetails.getAmount());
		s.setRefunded(paymentDetails.getRefunded());
		s.setReceipts(paymentDetails.getReceipts());
		s.setTickets(paymentDetails.getTickets());
		s.setCreditCard(paymentDetails.getCreditCard());


		return paymentRepository.save(s);
	}
	
	@Transactional
	public void deletePayment(Long id) {
		if (!paymentRepository.existsById(id)) {
			throw new EntityNotFoundException("Payment does not exist.");
		}
		paymentRepository.deleteById(id);
	}
    
}