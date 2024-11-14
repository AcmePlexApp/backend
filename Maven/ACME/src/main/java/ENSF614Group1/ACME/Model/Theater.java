package ENSF614Group1.ACME.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Theater {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String name;
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Seat> seats = new ArrayList<>();
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Showtime> showtimes = new ArrayList<>();
	
	// Getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public List<Seat> getSeats(){return seats;}
	public List<Showtime> getShowtimes() {return showtimes;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setSeats(List<Seat> seats) {this.seats = seats;}
	public void setShowtimes(List<Showtime> showtimes ) {this.showtimes = showtimes;}
	
	// Constructors
	public Theater() {}
	public Theater(String name) {
		this.name = name;
	}
	public Theater(Theater theater) {
		this.name = theater.name;
	}
	
	// Methods
	void addShowtime(Showtime showtime) {
		if (showtimes.contains(showtime)) {
			throw new IllegalArgumentException("Showtime already exists.");
		}
		showtimes.add(showtime);
	}
	
	void removeShowtime(Showtime showtime) {
		if (!showtimes.contains(showtime)) {
			throw new IllegalArgumentException("Showtime not found.");
		}
		showtimes.remove(showtime);
	}
	
}
