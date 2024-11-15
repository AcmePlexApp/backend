package ENSF614Group1.ACME.Model;

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
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	// Getters
	public Long getID() {return id;}
	public Movie getMovie() {return movie;}
	public LocalTime getTimeOfDay() {return timeOfDay;}
	
	// Setters
	public void setTimeOfDay(LocalTime timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	// Constructors
	public Showtime() {}
	public Showtime(Movie movie, LocalTime time) {
		this.movie = movie;
		this.timeOfDay = time;
	}
	public Showtime(Showtime showtime) {
		this.movie = showtime.movie;
		this.timeOfDay = showtime.timeOfDay;
	}
	
}
