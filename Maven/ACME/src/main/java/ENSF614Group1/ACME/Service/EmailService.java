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
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;
	
	@Transactional
	public Email createEmail(Email email) {
		return emailRepository.save(email);
	}
	
	public List<Email> getAllEmails(){
		return emailRepository.findAll();
	}
	
	public Email getEmailById(Long id){
		Optional<Email> email = emailRepository.findById(id);
		if(email.isEmpty()) {
			throw new EntityNotFoundException("Email does not exist.");
		}
		return email.get();
	}

	
	@Transactional
	public Email updateEmail(Long id, Email emailDetails) {
		Optional<Email> email = emailRepository.findById(id);
		if (email.isEmpty()) {
			throw new EntityNotFoundException("Email does not exist.");
		}
		Email s = email.get();
		s.setTitle(emailDetails.getTitle());
		return emailRepository.save(s);
	}
	
	@Transactional
	public void deleteEmail(Long id) {
		if (!emailRepository.existsById(id)) {
			throw new EntityNotFoundException("Email does not exist.");
		}
		emailRepository.deleteById(id);
	}
    
}