package ENSF614Group1.ACME.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Service.TheaterService;

@RestController
@RequestMapping("/theater")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;
	
	@GetMapping
	public ResponseEntity<List<Theater>> getAllTheaters(){
		return ResponseEntity.ok(theaterService.getAllTheaters());
	}
	
	@PostMapping
	public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        // Save the theater to the database
        Theater createdTheater = theaterService.createTheater(theater);
        // Return 201 created response with the saved theater and Location header
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTheater);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Theater> getTheaterById(@PathVariable Long id) {
	    Optional<Theater> theater = theaterService.getTheaterById(id);
	    if (theater.isEmpty()) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // If not found return 404 Not Found
	    }
	    // Otherwise return 200 OK with the theater object
	    return ResponseEntity.status(HttpStatus.OK).body(theater.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Theater> updateTheaterById(@PathVariable Long id, @RequestBody Theater theaterDetails){
		try {
			Theater theater = theaterService.updateTheater(id, theaterDetails);
			return ResponseEntity.status(HttpStatus.OK).body(theater);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
		try {
			theaterService.deleteTheater(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
