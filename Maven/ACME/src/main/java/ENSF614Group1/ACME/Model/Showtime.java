package ENSF614Group1.ACME.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
public class Showtime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private LocalTime timeOfDay;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "theater_id", nullable = false)
	private Theater theater;
	
	// Getters
	public Long getID() {return id;}
	public LocalTime getTimeOfDay() {return timeOfDay;}
	public Movie getMovie() {return movie;}
	public Theater getTheater() {return theater;}
	
	// Setters
	public void setTimeOfDay(LocalTime timeOfDay) {this.timeOfDay = timeOfDay;}
	public void setMovie(Movie movie) {this.movie = movie;}
	public void setTheater(Theater theater) {this.theater = theater;}
	
	// Constructors
	public Showtime() {}
	public Showtime(LocalTime time) {
		this.timeOfDay = time;
	}
	
}
