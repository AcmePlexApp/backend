package ENSF614Group1.ACME.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ENSF614Group1.ACME.Helpers.Views;
import ENSF614Group1.ACME.Model.Seat;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Service.TheaterService;

@RestController
@RequestMapping("/theater")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;
	
	@GetMapping
	@JsonView(Views.Basic.class)
	public ResponseEntity<List<Theater>> getAllTheaters(){
		return ResponseEntity.status(HttpStatus.OK).body(theaterService.getAllTheaters());
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Basic.class)
	public ResponseEntity<Theater> getTheaterById(@PathVariable Long id) {
	    Theater theater = theaterService.getTheaterById(id);
	    return ResponseEntity.status(HttpStatus.OK).body(theater);
	}
	
	@GetMapping("/{theaterId}/showtime/{showtimeId}/seats")
	@JsonView(Views.TheaterDetail.class)
	public ResponseEntity<List<Seat>> getSeats(@PathVariable Long showtimeId){
		List<Seat> seats = theaterService.getSeats(showtimeId);
		return ResponseEntity.status(HttpStatus.OK).body(seats);
	}	
	
}
