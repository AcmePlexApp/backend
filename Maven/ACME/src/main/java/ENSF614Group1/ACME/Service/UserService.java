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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	@Autowired private RegisteredUserRepository registeredUserRepository;
	@Autowired private CreditCardRepository creditCardRepository;
	@Autowired private CreditRepository creditRepository;
	
    @PersistenceContext private EntityManager entityManager;
	
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
	
	@Transactional
	public User addCreditToUser(Long id, Double amount) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new EntityNotFoundException("User does not exist.");
		}
		User s = user.get();
		Credit newCredit = new Credit(amount, s);
		s.addCredit(newCredit);
		return userRepository.save(s);
		
	}
	
	@Transactional
    public RegisteredUser register(Long userID, Long creditCardID) {
    	Optional<CreditCard> optCreditCard = creditCardRepository.findById(creditCardID);
		if (optCreditCard.isEmpty()) {
			throw new EntityNotFoundException("User could not be registered.  CreditCard does not exist.");
		}
		CreditCard creditCard = optCreditCard.get();
		
		// Specialization of a Generalized class requires manual SQL commands.
		entityManager.createNativeQuery("UPDATE user SET user_type = :newType WHERE id = :userId")
    	.setParameter("newType", RegisteredUser.RegisteredUserKey)
    	.setParameter("userId", userID)
    	.executeUpdate();
		// Flush and clear to reload repository data
		entityManager.flush();
		entityManager.clear();
		Optional<RegisteredUser> optRegisteredUser = registeredUserRepository.findById(userID);
		if (optRegisteredUser.isEmpty()) {
			throw new EntityNotFoundException("User could not be registered.  Cannot find registered user.");
		}
		RegisteredUser registeredUser = optRegisteredUser.get();
		registeredUser.setMembershipExpires();
		registeredUser.setCreditCard(creditCard);
		RegisteredUser savedRegisteredUser = registeredUserRepository.save(registeredUser);
		return savedRegisteredUser;
	}
	
	@Transactional
    public Double applyCredits(Long userID, Double amount) {
    	Optional<User> optUser = userRepository.findById(userID);
		if (optUser.isEmpty()) {
			throw new EntityNotFoundException("Credits could not be applied. User does not exist.");
		}
		User user = optUser.get();
		List<Credit> credits = user.getCredits();
		credits.sort((e1, e2) -> e1.getExpires().compareTo(e2.getExpires()));
		Double remaining = amount;
		int numCredits = credits.size();
		int creditIndex = 0;
		while((remaining > 0.0) && (creditIndex < numCredits)) {
			Double availableCredit = credits.get(creditIndex).availableMoney();
			Double deduction = Math.min(remaining, availableCredit);
			remaining -= deduction;
			credits.get(creditIndex).deduct(deduction);
			creditRepository.save(credits.get(creditIndex));
			user.updateCredit(credits.get(creditIndex));
			creditIndex += 1;
		}
		User savedUser = userRepository.save(user);
		cleanUpExpiredOrUsedCredits(savedUser);
		return remaining;
	}
	
	@Transactional
	public void cleanUpExpiredOrUsedCredits(User user) {
		List<Credit> credits = user.getCredits();
		List<Credit> remainingCredits = new ArrayList<>();
		int numCredits = credits.size();
		for(int i=0;i<numCredits;i++) {
			if(credits.get(i).isValid()) {
				remainingCredits.add(credits.get(i));
			} else {
				creditRepository.delete(credits.get(i));
			}
		}
		user.setCredits(remainingCredits);
		userRepository.save(user);
	}
    
}