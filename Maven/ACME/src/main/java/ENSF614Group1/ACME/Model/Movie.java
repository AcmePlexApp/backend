package ENSF614Group1.ACME.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ENSF614Group1.ACME.Helpers.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Movie {
	
	static int DESCRIPTION_LENGTH = 255;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Basic.class)
    private Long id;    
	
	@JsonView(Views.Basic.class)
    private String title; 
	
	@JsonView(Views.Basic.class)
    private String description;
	
	@JsonView(Views.Basic.class)
	private LocalDate releaseDate;
	
	@JsonView(Views.Basic.class)
	private String posterURL = "";
	
	@JsonView(Views.Basic.class)
	private String backdropURL = "";
	
	@JsonView(Views.Basic.class)
	private Double voteAverage = 0.0;
	
	@JsonView(Views.Basic.class)
	private Double popularity = 0.0;
	
	@JsonView(Views.Basic.class)
	private Long tmdbId = (long) 0;
    
	@OneToMany(mappedBy = "movie")
	@JsonView(Views.MovieDetail.class)
	private List<Theater> theaters = new ArrayList<>();
	
    // Getters
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public LocalDate getReleaseDate() {return releaseDate;}
    public List<Theater> getTheaters() {return theaters;}
    public Long getTMDBId() {return tmdbId;}
    
    // Setters
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}
    public void setTheaters(List<Theater> theaters) {this.theaters = theaters;}
    
    // Constructors
    public Movie() {}
    public Movie(String title, String description, LocalDate releaseDate) {
    	this.title = title;
    	this.description = description;
    	this.releaseDate = releaseDate;
    }
    
    public Movie(JSONObject fromTMDBJSON) {
    	if (fromTMDBJSON.getBoolean("adult") != false) {throw new IllegalArgumentException("Adult movies not allowed");}
    	this.title = fromTMDBJSON.getString("title");
    	String longDescription = fromTMDBJSON.getString("overview");
    	this.description = longDescription.length() > DESCRIPTION_LENGTH ? longDescription.substring(0, DESCRIPTION_LENGTH) : longDescription;
    	String releaseDateString = fromTMDBJSON.getString("release_date");
    	this.releaseDate = LocalDate.parse(releaseDateString);
    	this.posterURL = fromTMDBJSON.getString("poster_path");
    	this.backdropURL = fromTMDBJSON.getString("backdrop_path");
    	this.voteAverage = fromTMDBJSON.getDouble("vote_average");
    	this.popularity = fromTMDBJSON.getDouble("popularity");
    	this.tmdbId = fromTMDBJSON.getLong("id");
    }

}
