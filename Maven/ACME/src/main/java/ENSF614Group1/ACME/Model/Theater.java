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
	
	@ManyToMany(mappedBy = "theaters", cascade = CascadeType.ALL)
	private List<Movie> movies = new ArrayList<>();
	
	// Getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public List<Seat> getSeats(){return seats;}
	public List<Movie> getMovies() {return movies;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setSeats(List<Seat> seats) {this.seats = seats;}
	public void setMovies(List<Movie> movies ) {this.movies = movies;}
	
	// Constructors
	public Theater() {}
	public Theater(String name) {
		this.name = name;
	}
	public Theater(Theater theater) {
		this.name = theater.name;
	}
	
}
