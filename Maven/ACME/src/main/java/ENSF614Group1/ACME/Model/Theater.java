package ENSF614Group1.ACME.Model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Theater {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String name;
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Seat> seats;
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Showtime> showtimes;
	
	// Getters
	public Long getID() {return id;}
	public String getName() {return name;}
	public List<Seat> getSeats(){return seats;}
	public List<Showtime> getShowtimes() {return showtimes;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setSeats(List<Seat> seats) {this.seats = seats;}
	public void setShowtimes(List<Showtime> showtimes ) {this.showtimes = showtimes;}
	
	// Constructors
	public Theater() {}
	public Theater(String name, List<Seat> seats, List<Showtime> showtimes) {
		this.name = name;
		this.seats = seats;
		this.showtimes = showtimes;
	}
	public Theater(Theater theater) {
		this.name = theater.name;
		this.seats = theater.seats;
		this.showtimes = theater.showtimes;
	}
	
}
