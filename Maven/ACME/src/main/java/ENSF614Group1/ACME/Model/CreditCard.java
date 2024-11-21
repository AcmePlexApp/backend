package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String cardNumber;
    private String expiry;
    
    @ManyToOne()
    @JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = false)
	private Bank bank;
    
    public Long getID() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getCardNumber() {return cardNumber;}
    public String getExpiry() {return expiry;}
    public Bank getBank() {return bank;}
    
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setCardNumber(String cardNumber) {this.cardNumber = cardNumber;}
    public void setExpiry(String expiry) {this.expiry = expiry;}
    public void setBank(Bank bank) {this.bank = bank;}
    
    public CreditCard() {}
    
    public CreditCard(
    		String firstName,
    	    String lastName,
    	    String cardNumber,
    	    String expiry,
    	    Bank bank
    		) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.cardNumber = cardNumber;
    	this.expiry = expiry;
    	this.bank = bank;
    }
    
    public void charge(Double amount) {
    	System.out.println("CHARGED " + amount + " TO " + firstName + " " + lastName + "'s CREDITCARD " + cardNumber + " at Bank " + bank.getTitle());
    }
    
    public void refund(Double amount) {
    	System.out.println("REFUNDED " + amount + " TO " + firstName + " " + lastName + "'s CREDITCARD " + cardNumber + " at Bank " + bank.getTitle());
    }
    
}