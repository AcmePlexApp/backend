package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
    
    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;
    
    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;
    
    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;
    
    public Long getID() {return id;}
    public User getUser() {return user;}
    public Theater getTheater() {return theater;}
    public Seat getSeat() {return seat;}
    public Showtime getShowtime() {return showtime;}
    
    public void setUser(User user) {this.user = user;}
    public void setTheater(Theater theater) {this.theater = theater;}
    public void setSeat(Seat seat) {this.seat = seat;}
    public void setShowtime(Showtime showtime) {this.showtime = showtime;}
    
    public Ticket() {
    }
    
    public Ticket(User user, Theater theater, Seat seat, Showtime showtime) {
    	this.user = user;
    	this.theater = theater;
    	this.seat = seat;
    	this.showtime = showtime;
    }
    
}
