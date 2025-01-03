package ENSF614Group1.ACME.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ENSF614Group1.ACME.Helpers.Views;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Theater {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Basic.class)
    private Long id;
	
	@JsonView(Views.Basic.class)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = true)
	@JsonView(Views.Basic.class)
	private Movie movie;
	
	@OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonView(Views.Basic.class)
	private List<Showtime> showtimes = new ArrayList<>();
	
	// Getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public Movie getMovie() {return movie;}
	
	public List<Showtime> getShowtimes() {return showtimes;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setMovie(Movie movie) {this.movie = movie;}
	
	public void setShowtimes(List<Showtime> showtimes) {this.showtimes = showtimes;}
	
	// Constructors
	public Theater() {}
	public Theater(String name) {
		this.name = name;
		createShowtimes(LocalDate.now());
	}
	public Theater(String name, Movie movie) {
		this.name = name;
		this.movie = movie;
		LocalDate startDate = LocalDate.now();
		if (movie.getReleaseDate().isAfter(startDate)) {
			startDate = movie.getReleaseDate();
		}
		createShowtimes(startDate);
	}
	
	// Methods
	private void createShowtimes(LocalDate date) {
		for(int i = 0; i < 30; i++) {
			showtimes.add(new Showtime(LocalDateTime.of(date.plusDays(i), LocalTime.of(16, 0)), this));   // 4:00 PM
			showtimes.add(new Showtime(LocalDateTime.of(date.plusDays(i), LocalTime.of(19, 0)), this));   // 7:00 PM
			showtimes.add(new Showtime(LocalDateTime.of(date.plusDays(i), LocalTime.of(22, 0)), this));   // 10:00 PM
		}
	}
	
}