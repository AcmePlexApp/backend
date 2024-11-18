package ENSF614Group1.ACME.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Showtime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private LocalTime timeOfDay;
	
	@ManyToOne
	@JoinColumn(name = "theater_id", nullable = false)
	@JsonIgnore
	private Theater theater;
	
	// Getters
	public Long getId() {return id;}
	public LocalTime getTimeOfDay() {return timeOfDay;}
	public Theater getTheater() {return theater;}
	
	// Setters
	public void setTimeOfDay(LocalTime timeOfDay) {this.timeOfDay = timeOfDay;}
	public void setTheater(Theater theater) {this.theater = theater;}
	
	// Constructors
	public Showtime() {}
	public Showtime(LocalTime time, Theater theater) {
		this.timeOfDay = time;
        this.theater = theater;
	}
	
}
