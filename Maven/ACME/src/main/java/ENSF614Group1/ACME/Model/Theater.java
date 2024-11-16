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
	
	@ManyToMany
	@JoinTable(
			name = "theater_movie",
			joinColumns = @JoinColumn(name = "theater_id"),
			inverseJoinColumns = @JoinColumn(name = "movie_id")
	)
	private List<Movie> movies = new ArrayList<>();
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Seat> seats = new ArrayList<>();
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Showtime> showtimes = new ArrayList<>();
	
	// Getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public List<Seat> getSeats(){return seats;}
	public List<Movie> getMovies() {return movies;}
	public List<Showtime> getShowtimes() {return showtimes;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setSeats(List<Seat> seats) {this.seats = seats;}
	public void setMovies(List<Movie> movies ) {this.movies = movies;}
	public void setShowtimes(List<Showtime> showtimes) {this.showtimes = showtimes;}
	
	// Constructors
	public Theater() {}
	public Theater(String name) {
		this.name = name;
	}
	
}
