package ENSF614Group1.ACME.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Showtime> showtimes = new ArrayList<>();
	
    private String title;    
    private String description;  
    private int durationInMinutes;
    
    @ManyToMany
	@JoinTable(
			name = "theater_movie", 
			joinColumns = @JoinColumn(name = "movie_id"), 
			inverseJoinColumns = @JoinColumn(name= "theater_id")
	)
    private List<Theater> theaters = new ArrayList<>();

    // Getters
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getDuration() {return durationInMinutes;}
    public List<Showtime> getShowtimes() {return showtimes;}
    public List<Theater> getTheaters() {return theaters;}
    
    // Setters
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setDuration(int duration) {this.durationInMinutes = duration;}
    public void setShowtimes(List<Showtime> showtimes) {this.showtimes = showtimes;}
    public void setTheaters(List<Theater> theaters) {this.theaters = theaters;}
    
    // Constructors
    public Movie() {}
    public Movie(String title, String description, int duration) {
    	this.title = title;
    	this.description = description;
    	this.durationInMinutes = duration;
    }
    public Movie(Movie movie) {
    	this.title = movie.title;
    	this.description = movie.description;
    	this.durationInMinutes = movie.durationInMinutes;
    }

}
