package ENSF614Group1.ACME.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import ENSF614Group1.ACME.Helpers.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Basic.class)
    private Long id;    
	
	@OneToMany(mappedBy = "movie")
	@JsonView(Views.Detailed.class)
	private List<Theater> theaters = new ArrayList<>();
	
	@JsonView(Views.Basic.class)
    private String title;   
	@JsonView(Views.Basic.class)
    private String description;
	@JsonView(Views.Basic.class)
    private int durationInMinutes;
    
    // Getters
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getDurationInMinutes() {return durationInMinutes;}
    public List<Theater> getTheaters() {return theaters;}
    
    // Setters
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setDurationInMinutes(int durationInMinutes) {this.durationInMinutes = durationInMinutes;}
    public void setTheaters(List<Theater> theaters) {this.theaters = theaters;}
    
    // Constructors
    public Movie() {}
    public Movie(String title, String description, int durationInMinutes) {
    	this.title = title;
    	this.description = description;
    	this.durationInMinutes = durationInMinutes;
    }
    
}
