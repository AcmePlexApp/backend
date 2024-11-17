package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
    
    @ManyToOne
    @JoinColumn(name = "payment_id")  // Foreign key column
    private Payment payment;
    
//    private Theatre theatre;
//    private Seat seat;
//    private Showtime showtime;
    
    public Long getID() {return id;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public Payment getPayment() {return payment;}
    public void setPayment(Payment payment) {this.payment = payment;}
    
    
    public Ticket() {
    	
    }
    
    public Ticket(Payment payment, User user) {
    	this.payment = payment;
    	this.user = user;
    }
    
}
