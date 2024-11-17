package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Credit {
	
	static int MONTHS_UNTIL_EXPIRY = 12;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Double amountUsed;
    private LocalDateTime expires;
    
    @ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key column
    private User user;
    
    public Long getID() {return id;}
    public Double getAmount() {return amount;}
    public Double getAmountUsed() {return amountUsed;}
    public LocalDateTime getExpires() {return expires;}
    
    public void setAmount(Double amount) {this.amount = amount;}
    public void setAmountUsed(Double amountUsed) {this.amountUsed = amountUsed;}
    public void setExpires(LocalDateTime expires) {this.expires = expires;}
    
    public Credit() {
    	
    }
    
    public Credit(Double amount, Double AmountUsed, LocalDateTime expires, User user) {
    	this.amount = amount;
    	this.amountUsed = amountUsed;
    	this.expires = expires;
    	this.user = user;    }
    
    public Credit(Double amount, User user) {
    	this.amount = amount;
    	this.amountUsed = 0.0;
    	this.expires = LocalDateTime.now().plusMonths(MONTHS_UNTIL_EXPIRY);
    	this.user = user;
    }
    
    public double availableMoney() {
    	return isValid() ? amount - amountUsed : 0.0;
    }
    
    public void deduct(Double amount) {
    	amountUsed = amountUsed + amount;
    }
    
    public boolean isValid() {
    	return (amount - amountUsed > 0.01) && this.expires.isAfter(LocalDateTime.now());
    }
    
}
