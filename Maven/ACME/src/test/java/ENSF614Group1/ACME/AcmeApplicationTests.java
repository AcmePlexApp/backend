package ENSF614Group1.ACME;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ENSF614Group1.ACME.Model.*;
import ENSF614Group1.ACME.Repository.*;
import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional // Reverts changes after each test.
class AcmeApplicationTests {
		
    @Autowired private UserRepository userRepository;
    @Autowired private RegisteredUserRepository registeredUserRepository;
    @Autowired private BankRepository bankRepository;
    @Autowired private CreditCardRepository creditCardRepository;
    @Autowired private CreditRepository creditRepository;

    
    @Autowired private EntityManager entityManager;  // Injecting EntityManager


	@Test
	void contextLoads() {
		// Leave this in - just checks whether the context loads properly.
	}
	
	@Test
    void testCreateBankAndCreditCard() {
		Bank bank = getTestBank();
		Bank savedBank = bankRepository.save(bank);
		CreditCard creditCard = getTestCreditCard(savedBank);
        CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        assertNotNull(savedCreditCard.getID());
        assertEquals(TestBankName, savedCreditCard.getBank().getTitle());
    }
	
	@Test
    void testCreateUserWithCreditsAndRegisterWithCreditCard() {
		Bank bank = getTestBank();
		Bank savedBank = bankRepository.save(bank);
		CreditCard creditCard = getTestCreditCard(savedBank);
        CreditCard savedCreditCard = creditCardRepository.save(creditCard);
		User user = getTestUser();
		User savedUser = userRepository.save(user);
		loadUserWithTestCredits(savedUser);
		Long savedUserID = savedUser.getID();
		specializeUserToRegisteredUser(savedUser);
		Optional<RegisteredUser> foundRegisteredUser = registeredUserRepository.findById(savedUserID);
		assertTrue(foundRegisteredUser.isPresent(), "User should be found by ID");
        assertTrue(userRepository.findById(savedUserID).isPresent()); // will STILL show up in userRepository
		RegisteredUser registeredUser = foundRegisteredUser.get();
		registeredUser.setMembershipExpires(TestMembershipExpires);
		registeredUser.setCreditCard(savedCreditCard);
		//userRepository.delete(savedUser);
		RegisteredUser savedRegisteredUser = registeredUserRepository.save(registeredUser);
		Long savedRegisteredUserID = savedRegisteredUser.getID();
        assertNotNull(savedRegisteredUser.getID());
        assertEquals(TestBankName, savedRegisteredUser.getCreditCard().getBank().getTitle());
        //assertEquals(savedUserID, savedRegisteredUserID); // The registeredUser should have a different ID.
        
        List<Credit> registeredCredits = savedRegisteredUser.getCredits();
        assertEquals(registeredCredits.size(), TestCreditsSize);
        
	}

	static String RegisteredUserKey = "registeredUser";
	static String TestUsername = "TestUsername";
	static String TestPassword = "TestPassword";
	static String TestEmail = "Test@Email";
	static String TestBankName = "TestBank";
	static String TestCardNumber = "1111222233334444";
	static String TestFirstName = "Test_FirstName";
	static String TestLastName = "Test_LastName";
	static String TestExpiry = "1122";
	static LocalDateTime TestMembershipExpires = LocalDateTime.now();
	static Double TestAmount = 10.0;
	static Double TestAmountUsed = 2.0;
	static int TestCreditsSize = 5;

	
	static Bank getTestBank() {
		Bank bank = new Bank(
				TestBankName
				);
		return bank;
	}
	
	static CreditCard getTestCreditCard(Bank bank) {
		CreditCard creditCard = new CreditCard(
				TestCardNumber,
				TestFirstName,
				TestLastName,
				TestExpiry,
				bank
				);
        return creditCard;
	}
	
	static User getTestUser() {
		User user = new User(
				TestUsername,
				TestPassword,
				TestEmail
				);
		return user;
	}
	
	Credit getTestCredit(boolean expired, User user) {
		LocalDateTime expiryDate = expired ? LocalDateTime.now().plusMonths(6) : LocalDateTime.now().minusMonths(6);
		Credit credit = new Credit(
				TestAmount,
				TestAmountUsed,
				expiryDate,
				user
				);
		return credit;
				
	}
	
	void loadUserWithTestCredits(User user) {
		for(int i=0; i<TestCreditsSize; i++) {
			Credit credit = getTestCredit(i % 2 == 0, user);
			Credit savedCredit = creditRepository.save(credit);
		}
	}
	
	void specializeUserToRegisteredUser(User user) {
		Long tempID = user.getID();
		entityManager.createNativeQuery("UPDATE user SET user_type = :newType WHERE id = :userId")
    	.setParameter("newType", RegisteredUserKey)
    	.setParameter("userId", tempID)
    	.executeUpdate();
		// Flush and clear to reload repository data
		entityManager.flush();
		entityManager.clear();
	}
}
