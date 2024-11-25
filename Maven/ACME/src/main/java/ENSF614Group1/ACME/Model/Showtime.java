package ENSF614Group1.ACME.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ENSF614Group1.ACME.Helpers.Views;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Showtime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Basic.class)
    private Long id;
	
	@JsonView(Views.Basic.class)
	private LocalDateTime dateTime;
	
	@ManyToOne
	@JoinColumn(name = "theater_id", nullable = false)
	@JsonIgnore
	private Theater theater;
	
	@OneToMany(mappedBy = "showtime", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JsonView(Views.TheaterDetail.class)
	private List<Seat> seats = new ArrayList<>();
	
	// Getters
	public Long getId() {return id;}
	public LocalDateTime getDateTime() {return dateTime;}
	public Theater getTheater() {return theater;}
	public List<Seat> getSeats(){return seats;}
	
	// Setters
	public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
	public void setTheater(Theater theater) {this.theater = theater;}
	public void setSeats(List<Seat> seats) {this.seats = seats;}
	
	// Constructors
	public Showtime() {}
	public Showtime(LocalDateTime dateTime, Theater theater) {
		this.dateTime = dateTime;
        this.theater = theater;
        createSeats();
	}
	
	// Methods
	private void createSeats() {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				Seat seat = new Seat(i, j, this);
				this.seats.add(seat);			}
		}
	}
	
}
