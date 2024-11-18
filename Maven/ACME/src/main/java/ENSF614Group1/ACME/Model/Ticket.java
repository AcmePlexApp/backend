package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
    
//    private Theatre theatre;
//    private Seat seat;
//    private Showtime showtime;
    
    public Long getID() {return id;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    
    
    public Ticket() {
    	
    }
    
    public Ticket(User user) {
    	this.user = user;
    }
    
}
