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
import ENSF614Group1.ACME.Model.Showtime;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Service.SeatService;
import ENSF614Group1.ACME.Service.ShowtimeService;
import ENSF614Group1.ACME.Service.TheaterService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/theater")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;
	
	@Autowired
	private SeatService seatService;
	
	// Add movie service
	
	@PostMapping
	public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
		Theater createdTheater = theaterService.createTheater(theater);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTheater);
	}
	
	@GetMapping
	public ResponseEntity<List<Theater>> getAllTheaters(){
		return ResponseEntity.status(HttpStatus.OK).body(theaterService.getAllTheaters());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Theater> getTheaterById(@PathVariable Long id) {
	    Theater theater = theaterService.getTheaterById(id);
	    return ResponseEntity.status(HttpStatus.OK).body(theater);
	}
	
	@GetMapping("/{id}/seats")
	public ResponseEntity<List<Seat>> getSeatsInTheater(@PathVariable Long id){
		Theater theater = theaterService.getTheaterById(id);
		List<Seat> seats = seatService.getSeatsInTheater(theater);
		return ResponseEntity.status(HttpStatus.OK).body(seats);
	}
	
	// GetMovies in theater
	
	@PutMapping("/{id}")
	public ResponseEntity<Theater> updateTheaterById(@PathVariable Long id, @RequestBody Theater theaterDetails){
		Theater theater = theaterService.updateTheater(id, theaterDetails);
		return ResponseEntity.status(HttpStatus.OK).body(theater);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTheaterById(@PathVariable Long id) {
		theaterService.deleteTheater(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
