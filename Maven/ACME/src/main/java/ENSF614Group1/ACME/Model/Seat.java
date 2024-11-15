package ENSF614Group1.ACME.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private int seatRow;
	private int seatNumber;
	
	@ManyToOne
	@JoinColumn(name = "theater_id")
	private Theater theater;
	
	// Getters
	public Long getId() {return id;}
	public int getSeatRow() {return seatRow;}
	public int getSeatNumber() {return seatNumber;}
	
	// Setters
	public void setSeatRow(int seatRow) {this.seatRow = seatRow;}
	public void setSeatNumber(int seat) {this.seatNumber = seat;}

	// Constructors
	public Seat() {}
	public Seat(int seatRow, int seat) {
		this.seatRow = seatRow;
		this.seatNumber = seat;
	}
	public Seat(Seat seat) {
		this.seatRow = seat.seatRow;
		this.seatNumber = seat.seatNumber;
	}
}
