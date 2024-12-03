package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
	
	static Double REFUND_RATE = 0.85;
			
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role = "";
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Credit> credits = new ArrayList<>();
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
    
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();
    
    public Long getID() {return id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public List<Credit> getCredits() {return credits;}
    public Cart getCart() {return cart;}
    public List<Ticket> getTickets(){return tickets;}
    public String getRole() {return role;}

    
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
    public void setCredits(List<Credit> credits) {this.credits = credits;}
    public void setCart(Cart cart) {this.cart = cart;}
    public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
    public void setRole(String role) {this.role = role;}
    
    public void addCredit(Credit credit) {
    	credits.add(credit);
    }
    
    public void updateCredit(Credit credit) {
    	Optional<Credit> optCredit = credits.stream().filter(cr -> cr.getID().equals(credit.getID())).findFirst();
    	if (optCredit.isPresent()) {
    		int index = credits.indexOf(optCredit.get());
    		credits.set(index, optCredit.get());
    	}
    }
    
    public User() {
    	
    }
    
    public User(
    		String username,
    		String password,
    		String email,
    		String role
    		)
    {
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.role = role;
    	this.cart = new Cart(this);
    	
    }
    
    
    public User(User user) {
    	this.id = user.id;
    	this.username = user.username;
    	this.password = user.password;
    	this.email = user.email;
    	this.role = user.role;
    	this.cart = new Cart(this);
    }
    
    public boolean isRegistered() {
    	return false;
    }
    
    public Double refundRate() {
    	return REFUND_RATE;
    }
    
    public CreditCard getCreditCard() {
    	return null;
    }

}
