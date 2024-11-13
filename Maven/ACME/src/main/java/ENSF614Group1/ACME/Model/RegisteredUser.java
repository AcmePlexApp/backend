package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("registeredUser")
public class RegisteredUser extends User {
	
	
	private LocalDateTime membershipExpires;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditCard_id", referencedColumnName = "id")
	private CreditCard creditCard;
	
	public LocalDateTime getMembershipExpires() {return membershipExpires;}
	public CreditCard getCreditCard() {return creditCard;}
	
	public void setMembershipExpires(LocalDateTime membershipExpires) {this.membershipExpires = membershipExpires;}
    public void setCreditCard(CreditCard creditCard) {this.creditCard = creditCard;}
    
    public RegisteredUser() {
    	super();
    }
    
    public RegisteredUser(User user, LocalDateTime membershipExpires, CreditCard creditCard ) {
    	super(user);
    	this.membershipExpires = membershipExpires;
    	this.creditCard = creditCard;
    }
    
}
