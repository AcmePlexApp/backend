package ENSF614Group1.ACME.Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Theater {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = true)
	@JsonIgnore
	private Movie movie;
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Showtime> showtimes = new ArrayList<>();
	
	// Getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public Movie getMovie() {return movie;}
	
	public List<Showtime> getShowtimes() {return showtimes;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setMovie(Movie movie) {this.movie = movie;}
	
	public void setShowtimes(List<Showtime> showtimes) {this.showtimes = showtimes;}
	
	// Constructors
	public Theater() {}
	public Theater(String name) {
		this.name = name;
		createShowtimes();
	}
	
	// Methods
	private void createShowtimes() {
	    showtimes.add(new Showtime(LocalTime.of(13, 0), this));   // 1:00 PM
	    showtimes.add(new Showtime(LocalTime.of(16, 0), this));   // 4:00 PM
	    showtimes.add(new Showtime(LocalTime.of(19, 0), this));   // 7:00 PM
	    showtimes.add(new Showtime(LocalTime.of(21, 0), this));   // 9:00 PM
	}
	
}
