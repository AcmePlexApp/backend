package ENSF614Group1.ACME.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ENSF614Group1.ACME.Helpers.Views;
import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Service.MovieService;
import ENSF614Group1.ACME.Service.TheaterService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private TheaterService theaterService;
	
	@Autowired
	private MovieService movieService;
	
	@PostMapping("/theater")
	@JsonView(Views.Basic.class)
	public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
		Theater createdTheater = theaterService.createTheater(theater.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTheater);
	}
	
	@PostMapping("/theater/{theaterId}/movie/{movieId}")
	@JsonView(Views.Basic.class)
	public ResponseEntity<String> addMovieToTheater(@PathVariable Long theaterId, @PathVariable Long movieId){
		theaterService.addMovieToTheater(theaterId, movieId);
		Theater theater = theaterService.getTheaterById(theaterId);
		Movie movie = movieService.getMovieById(movieId);
		String response = movie.getTitle() + " has been added to " + theater.getName();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/theater/{id}")
	@JsonView(Views.Basic.class)
	public ResponseEntity<Theater> updateTheaterById(@PathVariable Long id, @RequestBody Theater theaterDetails){
		Theater theater = theaterService.updateTheater(id, theaterDetails);
		return ResponseEntity.status(HttpStatus.OK).body(theater);
	}
	
	@DeleteMapping("/theater/{id}")
	public ResponseEntity<String> deleteTheaterById(@PathVariable Long id) {
		theaterService.deleteTheater(id);
		return ResponseEntity.status(HttpStatus.OK).body("Theater successfully removed.");
	}
	
	@DeleteMapping("/theater/{theaterId}/{movieId}")
	public ResponseEntity<String> deleteMovieFromTheater(@PathVariable Long theaterId, @PathVariable Long movieId){
		Theater theater = theaterService.getTheaterById(theaterId);
		Movie movie = movieService.getMovieById(movieId);
		theaterService.deleteMovieFromTheater(theaterId, movieId);
		String response = movie.getTitle() + " has been removed from " + theater.getName();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/movie")
	@JsonView(Views.MovieDetail.class)
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
		Movie createdMovie = movieService.createMovie(movie);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
	}
	
	@PutMapping("/movie/{id}")
	@JsonView(Views.MovieDetail.class)
	public ResponseEntity<Movie> updateMovieById(@PathVariable Long id, @RequestBody Movie movieDetails){
		Movie movie = movieService.updateMovieById(id, movieDetails);
		return ResponseEntity.status(HttpStatus.OK).body(movie);
		
	}
	
	@DeleteMapping("/movie/{id}")
	public ResponseEntity<String> deleteMovieById(@PathVariable Long id){
		movieService.deleteMovieById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Movie successfully deleted.");
	}
	
}
