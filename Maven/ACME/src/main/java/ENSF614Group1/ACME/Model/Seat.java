package ENSF614Group1.ACME.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Seat {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private int row;
	private int seatNumber;
	
	// Getters
	public Long getID() {return id;}
	public int getRow() {return row;}
	public int getSeatNumber() {return seatNumber;}
	
	// Setters
	public void setRow(int row) {this.row = row;}
	public void setSeatNumber(int seat) {this.seatNumber = seat;}

	// Constructors
	public Seat() {}
	public Seat(int row, int seat) {
		this.row = row;
		this.seatNumber = seat;
	}
	public Seat(Seat seat) {
		this.row = seat.row;
		this.seatNumber = seat.seatNumber;
	}
}
