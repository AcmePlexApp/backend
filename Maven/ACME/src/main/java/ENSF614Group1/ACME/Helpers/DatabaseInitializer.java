package ENSF614Group1.ACME.Helpers;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Repository.MovieRepository;
import ENSF614Group1.ACME.Repository.TheaterRepository;
import ENSF614Group1.ACME.Service.TMDBService;
import ENSF614Group1.ACME.Service.TheaterService;
import jakarta.transaction.Transactional;

@Component
public class DatabaseInitializer {
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TMDBService tmdbService;
	
	@Autowired
	private TheaterService theaterservice;
	
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void initializeDatabase() {
		// Check if there is existing theater entities
		if(theaterRepository.count() != 0) {
			// if theater table has entities, skips initialization
			return;
		}
		
		// Creates 8 now playing movies and 2 upcoming movies
		try {
			JSONArray nowPlayingJSONMovies = tmdbService.getNowPlayingFromTMDB();
			JSONArray upcomingJSONMovies = tmdbService.getMoviesByReleaseDate(LocalDate.now().plusDays(10), LocalDate.now().plusDays(40));
			System.out.println("Initializing Test Database...");
			for (int i = 1; i <= 8; i++) {
				JSONObject jsonMovie = nowPlayingJSONMovies.getJSONObject(i-1);
				Movie movie = new Movie(jsonMovie);
				movieRepository.save(movie);
				Theater theater = new Theater("Theater " + i, movie);
				theaterRepository.save(theater);
			}
			for (int i = 9 ; i <= 10; i++) {
				JSONObject jsonMovie = upcomingJSONMovies.getJSONObject(i-9);
				Movie movie = new Movie(jsonMovie);
				movieRepository.save(movie);
				Theater theater = new Theater("Theater " + i, movie);
				theaterRepository.save(theater);
			}
			System.out.println("Test Database Initialized Successfully");
		} catch (Exception e) {
			System.out.println("Error Initializing Test Database - " + e.getLocalizedMessage());
		}
		
//		// Creates 10 theaters with movies
//		for (int i = 1; i <= 10; i++) {
//			Theater theater = new Theater("Theater " + i, movieService.ge);
//			theaterRepository.save(theater);
//		}
		
//		for(long i = 1; i <=10; i++) {
//			theaterservice.addMovieToTheater(i, i);
//		}	
	}
	
}
