package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    
    private String theaterName;
    private String movieName;
    private LocalDateTime showtime;
    
    public Long getID() {return id;}
    public User getUser() {return user;}
    public Seat getSeat() {return seat;}
    public Cart getCart() {return cart;}
    public String getTheaterName() {return theaterName;}
    public String getMovieName() {return movieName;}
    public LocalDateTime getShowtime() {return showtime;}
    
    public void setUser(User user) {this.user = user;}
    public void setSeat(Seat seat) {this.seat = seat;}
    public void setCart(Cart cart) {this.cart = cart;}
    
    public Ticket() {
    }
    
    public Ticket(Cart cart, Seat seat) {
    	this.user = null;
    	this.cart = cart;
    	this.seat = seat;
    	this.theaterName = seat.getShowtime().getTheater().getName();
    	this.movieName = seat.getShowtime().getTheater().getMovie().getTitle();
    	this.showtime = seat.getShowtime().getDateTime();
    }
    
}
