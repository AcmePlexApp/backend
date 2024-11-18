package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("registeredUser")
public class RegisteredUser extends User {
	
	static double CREDIT_FRACTION = 0.0;
	
	static int MONTHS_UNTIL_MEMBERSHIP_RENEWAL = 12;
		
	public static String RegisteredUserKey = "registeredUser";
	
	private LocalDateTime membershipExpires;
	
	@OneToOne()
    @JoinColumn(name = "creditCard_id", referencedColumnName = "id")
	private CreditCard creditCard;
	
	public LocalDateTime getMembershipExpires() {return membershipExpires;}
	public CreditCard getCreditCard() {return creditCard;}
	
	public void setMembershipExpires(LocalDateTime membershipExpires) {this.membershipExpires = membershipExpires;}
	public void setMembershipExpires() {this.membershipExpires = LocalDateTime.now().plusMonths(MONTHS_UNTIL_MEMBERSHIP_RENEWAL);}
    public void setCreditCard(CreditCard creditCard) {this.creditCard = creditCard;}
    
    public RegisteredUser() {
    	super();
    }
    
    public RegisteredUser(User user, LocalDateTime membershipExpires, CreditCard creditCard ) {
    	super(user);
    	this.membershipExpires = membershipExpires;
    	this.creditCard = creditCard;
    }
    
    public boolean isMembershipValid() {
    	return this.membershipExpires.isAfter(LocalDateTime.now());
    }
    
    @Override
    public boolean isRegistered() {
    	return true;
    }
}
