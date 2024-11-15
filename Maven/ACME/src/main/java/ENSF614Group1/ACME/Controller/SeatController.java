package ENSF614Group1.ACME.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ENSF614Group1.ACME.Model.Seat;
import ENSF614Group1.ACME.Service.SeatService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/seat")
public class SeatController {
	
	@Autowired
	private SeatService seatService;
	
	@PostMapping
	public ResponseEntity<Seat> createSeat(@RequestBody Seat seat){
			Seat createdSeat = seatService.createSeat(seat);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdSeat);	
	}
	
	@GetMapping
	public ResponseEntity<List<Seat>> getAllSeats() {
		return ResponseEntity.status(HttpStatus.OK).body(seatService.getAllSeats());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
			Seat seat = seatService.getSeatById(id);
			return ResponseEntity.status(HttpStatus.OK).body(seat);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Seat> updateSeatById(@PathVariable Long id, @RequestBody Seat seatDetails){
			Seat seat = seatService.updateSeat(id, seatDetails);
			return ResponseEntity.status(HttpStatus.OK).body(seat);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSeatById(@PathVariable Long id){
			seatService.deleteSeat(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
