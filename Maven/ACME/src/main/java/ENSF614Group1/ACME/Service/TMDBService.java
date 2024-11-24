package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ENSF614Group1.ACME.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ENSF614Group1.ACME.Model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TMDBService {
	
	@Autowired private MovieService movieService;
	
	static String API_KEY = "30d1d9c9f66f1427cca9a10228a4268d";
	public JSONArray nowPlayingJSONMovies;
	public JSONArray upcomingJSONMovies;
			
	@EventListener(ApplicationReadyEvent.class)
	public void initializeTMDBService() {
		try {
			nowPlayingJSONMovies = getNowPlaying();
			upcomingJSONMovies = getUpcoming();
			System.out.println("Populating Movie Database from TMDB...");
			for (int i = 0; i < nowPlayingJSONMovies.length(); i++) {
	            JSONObject jsonMovie = nowPlayingJSONMovies.getJSONObject(i);
	            placeJSONMovieInDatabase(jsonMovie);
			}
			for (int i = 0; i < upcomingJSONMovies.length(); i++) {
	            JSONObject jsonMovie = upcomingJSONMovies.getJSONObject(i);
	            placeJSONMovieInDatabase(jsonMovie);
			}
			System.out.println("TMDBService Initialized Successfully");
			
		} catch (Exception e) {
			System.out.println("Error Initializing TMDBService - " + e.getLocalizedMessage());
		}
	}
	
	private void placeJSONMovieInDatabase(JSONObject jsonMovie) {
		Movie movie = new Movie(jsonMovie);
        try {
        	movieService.createTMDBMovie(movie);
        	System.out.print("+");
        } catch (DuplicateKeyException e) {
        	System.out.print("x");
        }
	}
	
	private JSONArray getMoviesFrom(String urlString) throws InterruptedException, IOException {
    	HttpRequest request = HttpRequest.newBuilder()
    		    .uri(URI.create(urlString + "&api_key=" + API_KEY))
    		    //.header("Authorization", "Bearer " + API_KEY)
    		    .method("GET", HttpRequest.BodyPublishers.noBody())
    		    .build();
    		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    		JSONObject object = new JSONObject(response.body());
    		return object.getJSONArray("results");
    }
	
    private JSONArray getNowPlaying() throws InterruptedException, IOException {
    	return getMoviesFrom("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1");
    }
    
    private JSONArray getUpcoming() throws InterruptedException, IOException {
    	return getMoviesFrom("https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1");
    }
    
    
}