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
public class RegisteredUserService {

	@Autowired private RegisteredUserRepository registeredUserRepository;
	@Autowired private CreditCardRepository creditCardRepository;
		
	@Transactional
	public RegisteredUser createRegisteredUser(RegisteredUser registeredUser) {
		return registeredUserRepository.save(registeredUser);
	}
	
	public List<RegisteredUser> getAllRegisteredUsers(){
		return registeredUserRepository.findAll();
	}
	
	public RegisteredUser getRegisteredUserById(Long id){
		Optional<RegisteredUser> registeredUser = registeredUserRepository.findById(id);
		if(registeredUser.isEmpty()) {
			throw new EntityNotFoundException("RegisteredUser does not exist.");
		}
		return registeredUser.get();
	}

	
	@Transactional
	public RegisteredUser updateRegisteredUser(Long id, RegisteredUser registeredUserDetails) {
		Optional<RegisteredUser> registeredUser = registeredUserRepository.findById(id);
		if (registeredUser.isEmpty()) {
			throw new EntityNotFoundException("RegisteredUser does not exist.");
		}
		RegisteredUser s = registeredUser.get();
		s.setMembershipExpires(registeredUserDetails.getMembershipExpires());
		s.setCreditCard(registeredUserDetails.getCreditCard());
		return registeredUserRepository.save(s);
	}
	
	@Transactional
	public void deleteRegisteredUser(Long id) {
		if (!registeredUserRepository.existsById(id)) {
			throw new EntityNotFoundException("RegisteredUser does not exist.");
		}
		registeredUserRepository.deleteById(id);
	}
}