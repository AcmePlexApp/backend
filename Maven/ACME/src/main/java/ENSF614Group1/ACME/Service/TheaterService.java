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
public class TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Transactional
	public Theater createTheater(String name) {
		Theater theater = new Theater(name);
		return theaterRepository.save(theater);
	}
	
	public List<Theater> getAllTheaters(){
		return theaterRepository.findAll();
	}
	
	public Theater getTheaterById(Long id){
		Optional<Theater> theater = theaterRepository.findById(id);
		if(theater.isEmpty()) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		return theater.get();
	}
	
	// Does not alter seats or showtimes
	@Transactional
	public Theater updateTheater(Long id, Theater theaterDetails) {
		Optional<Theater> theater = theaterRepository.findById(id);
		if (theater.isEmpty()) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		Theater t = theater.get();
		t.setName(theaterDetails.getName());;
		t.setMovie(theaterDetails.getMovie());
		return theaterRepository.save(t);
	}
	
	@Transactional
	public void deleteTheater(Long id) {
		if (!theaterRepository.existsById(id)) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		theaterRepository.deleteById(id);
	}
	
	@Transactional
	public void addMovieToTheater(Long theaterId, Long movieId) {
		Optional<Theater> theater = theaterRepository.findById(theaterId);
		if (theater.isEmpty()) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		Optional<Movie> movie = movieRepository.findById(movieId);
		if (movie.isEmpty()) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		Theater t = theater.get();
		Movie m = movie.get();
		t.setMovie(m);
		theaterRepository.save(t);
	}
	
	@Transactional
	public void deleteMovieFromTheater(Long theaterId, Long movieId) {
		Optional<Theater> theater = theaterRepository.findById(theaterId);
		if (theater.isEmpty()) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		Optional<Movie> movie = movieRepository.findById(movieId);
		if (movie.isEmpty()) {
			throw new EntityNotFoundException("Movie does not exist.");
		}
		Theater t = theater.get();
		Movie m = movie.get();
		if(t.getMovie() == null || !t.getMovie().getId().equals(m.getId())) {
			throw new EntityNotFoundException("Movie not at theater.");
		}
		t.setMovie(null);
		theaterRepository.save(t);
	}
}
