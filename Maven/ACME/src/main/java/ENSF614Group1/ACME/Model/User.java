package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
			
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Credit> credits = new ArrayList<>();
    
    public Long getID() {return id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public List<Credit> getCredits() {return credits;}
    
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
    public void setCredits(List<Credit> credits) {this.credits = credits;}
    
    public void addCredit(Credit credit) {
    	credits.add(credit);
    }
    
    public void removeCredit(Credit credit) {
    	Optional<Credit> optCredit = credits.stream().filter(cr -> cr.getID().equals(credit.getID())).findFirst();
    	if (optCredit.isPresent()) {
    		credits.remove(optCredit.get());
    	}
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
    		String email
    		)
    {
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	
    }
    
    
    public User(User user) {
    	this.id = user.id;
    	this.username = user.username;
    	this.password = user.password;
    	this.email = user.email;
    }
    
    public boolean isRegistered() {
    	return false;
    }

}
