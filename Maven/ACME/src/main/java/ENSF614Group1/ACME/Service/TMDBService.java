package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TMDBService {
		
	static String API_KEY = "30d1d9c9f66f1427cca9a10228a4268d";
	
	private List<Movie> nowPlaying = new ArrayList<>();
	private List<Movie> upcoming = new ArrayList<>();
	
	public List<Movie> getNowPlaying() {return nowPlaying;}
	public List<Movie> getUpcoming() {return upcoming;}
	
	public Optional<Movie> getNowPlayingByTitle(String withTitle) {
		return nowPlaying.stream()
            .filter(movie -> movie.getTitle().equals(withTitle))
            .findFirst();
	}
	
	public Optional<Movie> getUpcomingByTitle(String withTitle) {
		return upcoming.stream()
            .filter(movie -> movie.getTitle().equals(withTitle))
            .findFirst();
	}
	
	public void initializeTMDBService() {
		try {
			JSONArray nowPlayingJSONMovies = getNowPlayingFromTMDB();
			JSONArray upcomingJSONMovies = getUpcomingFromTMDB();
			System.out.println("Initializing TMDBService...");
			
			for (int i = 0; i < nowPlayingJSONMovies.length(); i++) {
	            JSONObject jsonMovie = nowPlayingJSONMovies.getJSONObject(i);
	            nowPlaying.add(new Movie(jsonMovie));
			}
			for (int i = 0; i < upcomingJSONMovies.length(); i++) {
	            JSONObject jsonMovie = upcomingJSONMovies.getJSONObject(i);
	            upcoming.add(new Movie(jsonMovie));
	        }
			System.out.println("TMDBService Initialized Successfully");
			
		} catch (Exception e) {
			System.out.println("Error Initializing TMDBService - " + e.getLocalizedMessage());
		}
	}
	
	public JSONArray getMoviesFromTMDB(String urlString) throws InterruptedException, IOException {
    	HttpRequest request = HttpRequest.newBuilder()
    		    .uri(URI.create(urlString + "&api_key=" + API_KEY))
    		    //.header("Authorization", "Bearer " + API_KEY)
    		    .method("GET", HttpRequest.BodyPublishers.noBody())
    		    .build();
    		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    		JSONObject object = new JSONObject(response.body());
    		return object.getJSONArray("results");
    }
	
    public JSONArray getNowPlayingFromTMDB() throws InterruptedException, IOException {
    	return getMoviesFromTMDB("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1");
    }
    
    public JSONArray getUpcomingFromTMDB() throws InterruptedException, IOException {
    	return getMoviesFromTMDB("https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1");
    }
    
    public JSONArray getMoviesByReleaseDate(LocalDate startDate, LocalDate endDate) throws InterruptedException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);
        String url = String.format("https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=%s&primary_release_date.lte=%s", formattedStartDate, formattedEndDate);
        
        return getMoviesFromTMDB(url);
    }
}