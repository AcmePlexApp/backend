package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Boolean refunded = false;
    
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
    
    @OneToMany()
    private List<Ticket> tickets = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "creditcard_id", referencedColumnName = "id", nullable = false) // No unique constraint
    private CreditCard creditCard;
    
    @OneToMany()
    private List<Receipt> receipts = new ArrayList<>();
    
    public Long getID() {return id;}
    
    public Double getAmount() {return amount;}
    public Boolean getRefunded() {return refunded;}
    
    public User getUser() {return user;}
    public List<Ticket> getTickets() {return tickets;}
    public CreditCard getCreditCard() {return creditCard;}
    public List<Receipt> getReceipts() {return receipts;}
    
    public void setAmount(Double amount) {this.amount = amount;}
    public void setRefunded(Boolean refunded) {this.refunded = refunded;}
    
    public void setUser(User user) {this.user = user;}
    public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
    public void setCreditCard(CreditCard creditCard) {this.creditCard = creditCard;}
    public void setReceipts(List<Receipt> receipts) {this.receipts = receipts;}
        
    public Payment() {
    }
    
    public Payment(Double amount, Boolean refunded, User user, ArrayList<Ticket> tickets, CreditCard creditCard, ArrayList<Receipt> receipts) {
    	this.amount = amount;    	
    	this.refunded = refunded;
    	this.creditCard = creditCard;
    	this.user = user;
    	this.tickets = tickets;
    	this.receipts = receipts;
    }
    
    public void addReceipt(Receipt receipt) {
    	receipts.add(receipt);
    }
    
    public void removeReceipt(Receipt receipt) {
    	Optional<Receipt> optReceipt = receipts.stream().filter(obj -> obj.getID().equals(receipt.getID())).findFirst();
    	if (optReceipt.isPresent()) {
    		receipts.remove(optReceipt.get());
    	}
    }
    
    public void addTicket(Ticket ticket) {
    	tickets.add(ticket);
    }
    
    public void removeTicket(Ticket ticket) {
    	Optional<Ticket> optTicket = tickets.stream().filter(obj -> obj.getID().equals(ticket.getID())).findFirst();
    	if (optTicket.isPresent()) {
    		tickets.remove(optTicket.get());
    	}
    }
    
    
}
