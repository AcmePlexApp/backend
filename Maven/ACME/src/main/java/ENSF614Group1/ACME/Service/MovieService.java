package ENSF614Group1.ACME.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ENSF614Group1.ACME.Model.CreditCard;
import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.NewMovieNotification;
import ENSF614Group1.ACME.Model.Payment;
import ENSF614Group1.ACME.Model.Receipt;
import ENSF614Group1.ACME.Model.RegisteredUser;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Model.Ticket;
import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Repository.MovieRepository;
import ENSF614Group1.ACME.Repository.NewMovieNotificationRepository;
import ENSF614Group1.ACME.Repository.RegisteredUserRepository;
import ENSF614Group1.ACME.Repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MovieService {

	@Autowired
	public MovieRepository movieRepository;
	
	@Autowired
	public TheaterRepository theaterRepository;
	
	@Autowired
	public RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	public NewMovieNotificationRepository newMovieNotificationRepository;
	
	@Autowired
	public EmailService emailService;
	
	
	
	@Transactional
	public Movie createMovie(Movie movie) {
		
		Movie saved = movieRepository.save(movie);
		createNewMovieNotifications(saved);
		return saved;
	}
	
	@Transactional
	public Movie createTMDBMovie(Movie movie) throws DuplicateKeyException {
		if(movieRepository.existsBytmdbId(movie.getTMDBId())) {throw new DuplicateKeyException("Movie already in database"); }
		Movie saved = movieRepository.save(movie);
		createNewMovieNotifications(saved);
		return saved;
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
	
	public List<Movie> getUpcomingMovies() {
		List<Movie> allMovies = movieRepository.findAll();
		List<Movie> upcomingMovies = new ArrayList<>();
		for(Movie movie: allMovies) {
			if(movie.getReleaseDate().isAfter(LocalDate.now())) {
				upcomingMovies.add(movie);
			}
		}
		return upcomingMovies;
	}
	
	public List<Movie> getReleasedMovies() {
		List<Movie> allMovies = movieRepository.findAll();
		List<Movie> releasedMovies = new ArrayList<>();
		for(Movie movie: allMovies) {
			if(movie.getReleaseDate().isBefore(LocalDate.now())) {
				releasedMovies.add(movie);
			}
		}
		return releasedMovies;
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
		m.setReleaseDate(movieDetails.getReleaseDate());
		return movieRepository.save(m);
	}
	
	@Transactional
	public void deleteMovieById(Long id) {
		if(!movieRepository.existsById(id)) {
			throw new EntityNotFoundException("Movie does not exist.");
		}
		movieRepository.deleteById(id);
	}
	
	@Transactional
	private List<NewMovieNotification> createNewMovieNotifications(Movie movie) {
		LocalDate releaseDate = movie.getReleaseDate();
		List<NewMovieNotification> allNotifications = new ArrayList<>();
		if (!releaseDate.isAfter(LocalDate.now())) {return allNotifications;}
		List<RegisteredUser> allRegisteredUsers = registeredUserRepository.findAll();
		String body = movie.getTitle() + " is releasing on " + releaseDate.toString() + "\n\n";
		body = body.concat("Get your tickets on the AcmePlexApp now!!\n");
		for (RegisteredUser registeredUser : allRegisteredUsers) {
			String title = "Hey " + registeredUser.getUsername() + ", " + movie.getTitle() + " is releasing soon!";
			NewMovieNotification newMovieNotification = new NewMovieNotification(title, body, LocalDateTime.now(), registeredUser.getEmail());
			NewMovieNotification saved = newMovieNotificationRepository.save(newMovieNotification);
			emailService.sendEmail(saved);
			allNotifications.add(saved);
		}
		return allNotifications;
	}
	
}
