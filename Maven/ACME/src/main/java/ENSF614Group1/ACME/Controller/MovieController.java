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

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
//	@Autowired
//	private ShowtimeService showtimeService;
	
	@PostMapping
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
		Movie createdMovie = movieService.createMovie(movie);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
	}
	
	@GetMapping
	public ResponseEntity<List<Movie>> getAllMovies(){
		return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
		Movie movie = movieService.getMovieById(id);
		return ResponseEntity.status(HttpStatus.OK).body(movie);
	}
	
// Might need to consider this by theater instead
//	@GetMapping("/{id}/showtimes")
//	public ResponseEntity<List<Showtime>> getShowtimesByMovie(@PathVariable Long id){
//		Movie movie = movieService.getMovieById(id);
//		List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movie);
//		return ResponseEntity.status(HttpStatus.OK).body(showtimes);
//	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovieById(@PathVariable Long id, @RequestBody Movie movieDetails){
		Movie movie = movieService.updateMovieById(id, movieDetails);
		return ResponseEntity.status(HttpStatus.OK).body(movie);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovieById(@PathVariable Long id){
		movieService.deleteMovieById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
