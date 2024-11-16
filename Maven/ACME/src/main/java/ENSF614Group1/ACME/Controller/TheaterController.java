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

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Seat;
import ENSF614Group1.ACME.Model.Showtime;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Service.MovieService;
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
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ShowtimeService showtimeService;
	
	@PostMapping
	public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
		Theater createdTheater = theaterService.createTheater(theater.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTheater);
	}
	
	@PostMapping("/{theaterId}/movies/{movieId}")
	public ResponseEntity<String> addMovieToTheater(@PathVariable Long theaterId, @PathVariable Long movieId){
		theaterService.addMovieToTheater(theaterId, movieId);
		Theater theater = theaterService.getTheaterById(theaterId);
		Movie movie = movieService.getMovieById(movieId);
		String response = movie.getTitle() + " movie has been added to " + theater.getName() + " theater.";
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	// add showtime to theater
	
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
	public ResponseEntity<List<Seat>> getAllSeatsInTheater(@PathVariable Long id){
		Theater theater = theaterService.getTheaterById(id);
		List<Seat> seats = seatService.getSeatsInTheater(theater);
		return ResponseEntity.status(HttpStatus.OK).body(seats);
	}
	
	@GetMapping("/{id}/movies")
	public ResponseEntity<List<Movie>> getAllMoviesInTheater(@PathVariable Long id){
		Theater theater = theaterService.getTheaterById(id);
		List<Movie> movies = movieService.getMoviesInTheater(theater);
		return ResponseEntity.status(HttpStatus.OK).body(movies);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Theater> updateTheaterById(@PathVariable Long id, @RequestBody Theater theaterDetails){
		Theater theater = theaterService.updateTheater(id, theaterDetails);
		return ResponseEntity.status(HttpStatus.OK).body(theater);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTheaterById(@PathVariable Long id) {
		theaterService.deleteTheater(id);
		return ResponseEntity.status(HttpStatus.OK).body("Theater successfully removed.");
	}
	
	@DeleteMapping("/{theaterId}/movies/{movieId}")
	public ResponseEntity<String> deleteMovieFromTheater(@PathVariable Long theaterId, @PathVariable Long movieId){
		theaterService.deleteMovieFromTheater(theaterId, movieId);
		Theater theater = theaterService.getTheaterById(theaterId);
		Movie movie = movieService.getMovieById(movieId);
		String response = movie.getTitle() + " movie has been removed from " + theater.getName() + " theater.";
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	// delete showtime from theater
}
