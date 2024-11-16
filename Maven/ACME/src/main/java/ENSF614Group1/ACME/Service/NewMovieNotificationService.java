package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ENSF614Group1.ACME.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ENSF614Group1.ACME.Model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewMovieNotificationService {

	@Autowired
	private NewMovieNotificationRepository newMovieNotificationRepository;
	
	@Transactional
	public NewMovieNotification createNewMovieNotification(NewMovieNotification newMovieNotification) {
		return newMovieNotificationRepository.save(newMovieNotification);
	}
	
	public List<NewMovieNotification> getAllNewMovieNotifications(){
		return newMovieNotificationRepository.findAll();
	}
	
	public NewMovieNotification getNewMovieNotificationById(Long id){
		Optional<NewMovieNotification> newMovieNotification = newMovieNotificationRepository.findById(id);
		if(newMovieNotification.isEmpty()) {
			throw new EntityNotFoundException("NewMovieNotification does not exist.");
		}
		return newMovieNotification.get();
	}

	
	@Transactional
	public NewMovieNotification updateNewMovieNotification(Long id, NewMovieNotification newMovieNotificationDetails) {
		Optional<NewMovieNotification> newMovieNotification = newMovieNotificationRepository.findById(id);
		if (newMovieNotification.isEmpty()) {
			throw new EntityNotFoundException("NewMovieNotification does not exist.");
		}
		NewMovieNotification s = newMovieNotification.get();
		s.setTitle(newMovieNotificationDetails.getTitle());
		return newMovieNotificationRepository.save(s);
	}
	
	@Transactional
	public void deleteNewMovieNotification(Long id) {
		if (!newMovieNotificationRepository.existsById(id)) {
			throw new EntityNotFoundException("NewMovieNotification does not exist.");
		}
		newMovieNotificationRepository.deleteById(id);
	}
    
}