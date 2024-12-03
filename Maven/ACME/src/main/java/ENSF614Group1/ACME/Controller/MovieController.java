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
import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@GetMapping
	@JsonView(Views.MovieDetail.class)
	public ResponseEntity<List<Movie>> getAllMovies(){
		return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.MovieDetail.class)
	public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
		Movie movie = movieService.getMovieById(id);
		return ResponseEntity.status(HttpStatus.OK).body(movie);
	}
	
	@GetMapping("/upcoming")
	@JsonView(Views.MovieDetail.class)
	public ResponseEntity<List<Movie>> getUpcomingMovies(){
		return ResponseEntity.status(HttpStatus.OK).body(movieService.getUpcomingMovies());
	}
	
	@GetMapping("/released")
	@JsonView(Views.MovieDetail.class)
	public ResponseEntity<List<Movie>> getReleasedMovies(){
		return ResponseEntity.status(HttpStatus.OK).body(movieService.getReleasedMovies());
	}
	
}
