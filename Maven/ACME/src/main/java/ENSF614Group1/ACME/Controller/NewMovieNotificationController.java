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

import ENSF614Group1.ACME.Model.NewMovieNotification;
import ENSF614Group1.ACME.Service.NewMovieNotificationService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/newMovieNotification")
public class NewMovieNotificationController {
	
	@Autowired
	private NewMovieNotificationService newMovieNotificationService;
	
	@PostMapping
	public ResponseEntity<NewMovieNotification> createNewMovieNotification(@RequestBody NewMovieNotification newMovieNotification){
		NewMovieNotification createdNewMovieNotification = newMovieNotificationService.createNewMovieNotification(newMovieNotification);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdNewMovieNotification);	
	}
	
	@GetMapping
	public ResponseEntity<List<NewMovieNotification>> getAllNewMovieNotifications() {
		return ResponseEntity.status(HttpStatus.OK).body(newMovieNotificationService.getAllNewMovieNotifications());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NewMovieNotification> getNewMovieNotificationById(@PathVariable Long id) {
		NewMovieNotification newMovieNotification = newMovieNotificationService.getNewMovieNotificationById(id);
		return ResponseEntity.status(HttpStatus.OK).body(newMovieNotification);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<NewMovieNotification> updateNewMovieNotificationById(@PathVariable Long id, @RequestBody NewMovieNotification newMovieNotificationDetails){
		NewMovieNotification newMovieNotification = newMovieNotificationService.updateNewMovieNotification(id, newMovieNotificationDetails);
		return ResponseEntity.status(HttpStatus.OK).body(newMovieNotification);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNewMovieNotificationById(@PathVariable Long id){
		newMovieNotificationService.deleteNewMovieNotification(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
