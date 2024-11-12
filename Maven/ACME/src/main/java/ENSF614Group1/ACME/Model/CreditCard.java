package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    @OneToOne
    @JoinColumn(name = "registeredUser_id", nullable = true)
    private RegisteredUser registeredUser;
    
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getCardNumber() {return cardNumber;}
    public String getExpiry() {return expiry;}
    
}