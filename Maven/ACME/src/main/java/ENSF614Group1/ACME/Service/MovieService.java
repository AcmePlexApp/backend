package ENSF614Group1.ACME.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Repository.MovieRepository;
import ENSF614Group1.ACME.Repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MovieService {

	@Autowired
	public MovieRepository movieRepository;
	
	@Autowired
	public TheaterRepository theaterRepository;
	
	@Transactional
	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}
	
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	public Movie getMovieById(Long id) {
		Optional<Movie> movie = movieRepository.findById(id);
		if(movie.isEmpty()) {
			throw new EntityNotFoundException("Movie does not exist.");
		}
		return movie.get();
	}
	
	@Transactional
	public Movie updateMovieById(Long id, Movie movieDetails) {
		Optional<Movie> movie = movieRepository.findById(id);
		if(movie.isEmpty()) {
			throw new EntityNotFoundException("Movie does not exist.");
		}
		Movie m = movie.get();
		m.setTitle(movieDetails.getTitle());
		m.setDescription(movieDetails.getDescription());
		m.setDurationInMinutes(movieDetails.getDurationInMinutes());
		return movieRepository.save(m);
	}
	
	@Transactional
	public void deleteMovieById(Long id) {
		if(!movieRepository.existsById(id)) {
			throw new EntityNotFoundException("Movie does not exist.");
		}
		movieRepository.deleteById(id);
	}
	
}
