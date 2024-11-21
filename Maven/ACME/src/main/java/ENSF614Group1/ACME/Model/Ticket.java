package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;
    
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
//    private TicketStatus status;
    
    public Long getID() {return id;}
    public User getUser() {return user;}
    public Seat getSeat() {return seat;}
    public Cart getCart() {return cart;}
//    public TicketStatus getStatus() {return status;} 
    
    public void setUser(User user) {this.user = user;}
    public void setSeat(Seat seat) {this.seat = seat;}
    public void setCart(Cart cart) {this.cart = cart;}
//    public void setStatus(TicketStatus status) {this.status = status;}
    
    public Ticket() {
    }
    
    public Ticket(User user, Seat seat) {
    	this.user = user;
    	this.cart = user.getCart();
    	this.seat = seat;
//    	this.status = TicketStatus.INCART;
    }
    
//    public enum TicketStatus {
//	    INCART,
//	    PURCHASED
//	}
    
}
