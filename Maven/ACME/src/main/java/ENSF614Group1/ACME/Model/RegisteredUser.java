package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class RegisteredUser extends User {
	
	private LocalDateTime membershipExpires;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditCard_id", referencedColumnName = "id")
	private CreditCard creditCard;
}
