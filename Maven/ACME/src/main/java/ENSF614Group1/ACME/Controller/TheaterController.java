package ENSF614Group1.ACME.Controller;

import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonView;

import ENSF614Group1.ACME.Helpers.Views;
import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Seat;
import ENSF614Group1.ACME.Model.Showtime;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Service.MovieService;
import ENSF614Group1.ACME.Service.ShowtimeService;
import ENSF614Group1.ACME.Service.TheaterService;

@RestController
@RequestMapping("/theater")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ShowtimeService showtimeService;
	
	@PostMapping
	@JsonView(Views.Basic.class)
	public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
		Theater createdTheater = theaterService.createTheater(theater.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTheater);
	}
	
	@PostMapping("/{theaterId}/movie/{movieId}")
	@JsonView(Views.Basic.class)
	public ResponseEntity<String> addMovieToTheater(@PathVariable Long theaterId, @PathVariable Long movieId){
		theaterService.addMovieToTheater(theaterId, movieId);
		Theater theater = theaterService.getTheaterById(theaterId);
		Movie movie = movieService.getMovieById(movieId);
		String response = movie.getTitle() + " has been added to " + theater.getName();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	// Potentially add post methods for adding seats and showtime. At creation, Theaters generate generic seats and showtimes.
	// Currently setup so that seats and showtimes are cannot be altered.
	
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
	public ResponseEntity<List<Seat>> getSeats(@PathVariable Long theaterId, @PathVariable Long showtimeId){
		List<Seat> seats = theaterService.getSeats(theaterId, showtimeId);
		return ResponseEntity.status(HttpStatus.OK).body(seats);
	}
	
	
	@PutMapping("/{id}")
	@JsonView(Views.Basic.class)
	public ResponseEntity<Theater> updateTheaterById(@PathVariable Long id, @RequestBody Theater theaterDetails){
		Theater theater = theaterService.updateTheater(id, theaterDetails);
		return ResponseEntity.status(HttpStatus.OK).body(theater);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTheaterById(@PathVariable Long id) {
		theaterService.deleteTheater(id);
		return ResponseEntity.status(HttpStatus.OK).body("Theater successfully removed.");
	}
	
	@DeleteMapping("/{theaterId}/{movieId}")
	public ResponseEntity<String> deleteMovieFromTheater(@PathVariable Long theaterId, @PathVariable Long movieId){
		Theater theater = theaterService.getTheaterById(theaterId);
		Movie movie = movieService.getMovieById(movieId);
		theaterService.deleteMovieFromTheater(theaterId, movieId);
		String response = movie.getTitle() + " has been removed from " + theater.getName();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
