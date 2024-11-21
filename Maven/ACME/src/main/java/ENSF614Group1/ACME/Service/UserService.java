package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired private BankRepository bankRepository;
	@Autowired private CreditRepository creditRepository;
	
    @PersistenceContext private EntityManager entityManager;
	
	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public User createUser(String username, String password, String email) throws IllegalArgumentException {
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		String formattedUsername = UserService.checkAndFormatString(username);
		String formattedEmail = UserService.checkAndFormatString(email);
		
		if (userRepository.existsByUsername(formattedUsername)) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(formattedEmail)) {
            throw new IllegalArgumentException("Email is already registered");
        }
		
		User user = new User();
		user.setUsername(formattedUsername);
		user.setPassword(encodedPassword);
		user.setEmail(formattedEmail);
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
	
	public Boolean isUserRegistered(long id) {
		User user = getUserById(id);
		return user.isRegistered();
	}
	
	public User loadByUsername(String username) throws EntityNotFoundException {
		String formattedString = UserService.checkAndFormatString(username);
		User user = userRepository.findByUsername(formattedString);
		if(user == null) {
			throw new EntityNotFoundException("User does not exist.");
		}
		return user;
	}
	
	@Transactional
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User does not exist.");
		}
		userRepository.deleteById(id);
	}
	
	@Transactional
	public void purchase(Long id, CreditCard cc, Double amount, boolean applyCredits) {
		Optional<User> optUser = userRepository.findById(id);
		if (optUser.isEmpty()) {
			throw new EntityNotFoundException("User does not exist.");
		}
		User user = optUser.get();
		CreditCard creditCard = null;
		if (cc == null) { // ie: can leave out CC if they are registered
			CreditCard registeredCard = user.getCreditCard();
			if (registeredCard == null) { // They are not registered
				throw new RuntimeException("User is not registered and credit card info was not provided.");
			}
			creditCard = registeredCard;
		} else { // Credit card info was provided.
			creditCard = creditCardRepository.save(cc); // Create it.
		}
		
		Double remainingAmount = amount;
		if (applyCredits) {
			remainingAmount = applyCredits(user.getID(), amount);
		}
		Optional<Bank> optBank = bankRepository.findById(creditCard.getBank().getID());
		if (optBank.isEmpty()) {
			throw new EntityNotFoundException("User could not purchase.  Cannot find bank.");
		}
		Bank bank = optBank.get();
		creditCard.setBank(bank);
		creditCard.charge(remainingAmount);
	}
	
	@Transactional
	public void refund(Long id, CreditCard cc, Double amount) {
		Optional<User> optUser = userRepository.findById(id);
		if (optUser.isEmpty()) {
			throw new EntityNotFoundException("User does not exist.");
		}
		User user = optUser.get();
		CreditCard creditCard = null;
		if (cc == null) { // ie: can leave out CC if they are registered
			CreditCard registeredCard = user.getCreditCard();
			if (registeredCard == null) { // They are not registered
				throw new RuntimeException("User is not registered and credit card info was not provided.");
			}
			creditCard = registeredCard;
		} else { // Credit card info was provided.
			Optional<CreditCard> optCreditCard = creditCardRepository.findById(cc.getID()); // Find it.
			if (optCreditCard.isEmpty()) {
				throw new EntityNotFoundException("Credit Card does not exist.");
			}
			creditCard = optCreditCard.get();
		}
		Optional<Bank> optBank = bankRepository.findById(creditCard.getBank().getID());
		if (optBank.isEmpty()) {
			throw new EntityNotFoundException("User could not purchase.  Cannot find bank.");
		}
		Bank bank = optBank.get();
		creditCard.setBank(bank);
		Double creditAmount = amount * (1.0 - user.refundRate());
		Double refundAmount = amount - creditAmount;
		if (creditAmount > 0.001) {
			addCreditToUser(user.getID(), creditAmount);
		}
		creditCard.refund(refundAmount);		
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
    public RegisteredUser register(Long userID, CreditCard cc) {
		Optional<User> optUser = userRepository.findById(userID);
		if (optUser.isEmpty()) {
			throw new EntityNotFoundException("User could not be registered.  User does not exist.");
		}
		User user = optUser.get();
		if (user.isRegistered()) {
			throw new RuntimeException("User could not be registered.  User is already registered");
		}
		Optional<Bank> optBank = bankRepository.findById(cc.getBank().getID());
		if (optBank.isEmpty()) {
			throw new EntityNotFoundException("User could not be registered.  Cannot find bank.");
		}
		Bank bank = optBank.get();
		cc.setBank(bank);
		CreditCard creditCard = creditCardRepository.save(cc);
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
	
	static private String checkAndFormatString(String string) throws IllegalArgumentException {
		if (string.isBlank()) {
			throw new IllegalArgumentException("Empty Field is not allowed");
		}
		return string.trim().toLowerCase();
	}
    
}