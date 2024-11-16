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
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User getUserById(Long id){
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new EntityNotFoundException("User does not exist.");
		}
		return user.get();
	}

	
	@Transactional
	public User updateUser(Long id, User userDetails) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new EntityNotFoundException("User does not exist.");
		}
		User s = user.get();
		
		s.setUsername(userDetails.getUsername());
		s.setPassword(userDetails.getPassword());
		s.setEmail(userDetails.getEmail());
		s.setCredits(userDetails.getCredits());

		return userRepository.save(s);
	}
	
	@Transactional
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist.");
		}
		userRepository.deleteById(id);
	}
    
}