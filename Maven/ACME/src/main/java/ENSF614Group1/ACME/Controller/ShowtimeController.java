package ENSF614Group1.ACME.Controller;

import java.util.List;

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

import ENSF614Group1.ACME.Model.Showtime;
import ENSF614Group1.ACME.Service.ShowtimeService;

@RestController
@RequestMapping("/showtime")
public class ShowtimeController {

	@Autowired
	private ShowtimeService showtimeService;
	
	@PostMapping
	public ResponseEntity<Showtime> createShowtime(@RequestBody Showtime showtime){
		Showtime createdShowtime = showtimeService.createShowtime(showtime);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdShowtime);	
	}
	
	@GetMapping
	public ResponseEntity<List<Showtime>> getAllShowtimes(){
		return ResponseEntity.status(HttpStatus.OK).body(showtimeService.getAllShowtimes());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id){
		Showtime showtime = showtimeService.getShowtimeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(showtime);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Showtime> updateShowtimeById(@PathVariable Long id, @RequestBody Showtime showtimeDetails){
		Showtime showtime = showtimeService.updateShowtimeById(id, showtimeDetails);
		return ResponseEntity.status(HttpStatus.OK).body(showtime);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShowtimeById(@PathVariable Long id){
		showtimeService.deleteShowtimeById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
