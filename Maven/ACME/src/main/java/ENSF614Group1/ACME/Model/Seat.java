package ENSF614Group1.ACME.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ENSF614Group1.ACME.Helpers.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seat {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.TheaterDetail.class)
    private Long id;
	
	@JsonView(Views.TheaterDetail.class)
	private int seatRow;
	
	@JsonView(Views.TheaterDetail.class)
	private int seatNumber;
	
	@Enumerated(EnumType.STRING)
	@JsonView(Views.TheaterDetail.class)
    private SeatStatus status;
	
	@JsonView(Views.TheaterDetail.class)
	private double cost;
	
	@ManyToOne
	@JoinColumn(name = "showtime_id", nullable = false)
	@JsonIgnore
	private Showtime showtime;
	
	@OneToOne(mappedBy = "seat")
	@JsonIgnore
	private Ticket ticket;
	
	
	// Getters
	public Long getId() {return id;}
	public int getSeatRow() {return seatRow;}
	public int getSeatNumber() {return seatNumber;}
	public SeatStatus getStatus() {return status;}
	public double getCost() {return cost;}
	public Showtime getShowtime() {return showtime;}
	
	// Setters
	public void setSeatRow(int seatRow) {this.seatRow = seatRow;}
	public void setSeatNumber(int seatNumber) {this.seatNumber = seatNumber;}
	public void setStatus(SeatStatus status) {this.status = status;}
	public void setCost(double cost) {this.cost = cost;}
	public void setShowtime(Showtime showtime) {this.showtime = showtime;}
		
	// Constructors
	public Seat() {}
	public Seat(int seatRow, int seatNumber, Showtime showtime) {
		this.seatRow = seatRow;
		this.seatNumber = seatNumber;
		this.status = SeatStatus.AVAILABLE;
		this.cost = 10.99;
		this.showtime = showtime;
	}
	
	public enum SeatStatus {
	    AVAILABLE,
	    INCART,
	    BOOKED
	}
}
