package ENSF614Group1.ACME.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
	
    private String title;    
    private String description;
    private int durationInMinutes;
    
    // Getters
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getDurationInMinutes() {return durationInMinutes;}
    
    // Setters
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setDurationInMinutes(int durationInMinutes) {this.durationInMinutes = durationInMinutes;}
    
    // Constructors
    public Movie() {}
    public Movie(String title, String description, int durationInMinutes) {
    	this.title = title;
    	this.description = description;
    	this.durationInMinutes = durationInMinutes;
    }
    
}
