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
import ENSF614Group1.ACME.Service.*;
import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional // Reverts changes after each test.
class AcmeApplicationTests {
		    
    @Autowired private EntityManager entityManager;  // Injecting EntityManager

    @Autowired private UserService userService;
    @Autowired private RegisteredUserService registeredUserService;
    @Autowired private BankService bankService;
    @Autowired private CreditCardService creditCardService;
    @Autowired private CreditService creditService;

	@Test
	void contextLoads() {
		// Leave this in - just checks whether the context loads properly.
	}
	
	@Test
    void testCreateBankAndCreditCard() {
		Bank bank = getTestBank();
		Bank savedBank = bankService.createBank(bank);
		CreditCard creditCard = getTestCreditCard(savedBank);
        CreditCard savedCreditCard = creditCardService.createCreditCard(creditCard);
        assertNotNull(savedCreditCard.getID());
        assertEquals(TestBankName, savedCreditCard.getBank().getTitle());
    }
	
	@Test
    void testCreateUserWithCreditsAndRegisterWithCreditCard() {
		Bank bank = getTestBank();
		Bank savedBank = bankService.createBank(bank);
		CreditCard creditCard = getTestCreditCard(savedBank);
        CreditCard savedCreditCard = creditCardService.createCreditCard(creditCard);
		User user = getTestUser();
		User savedUser = userService.createUser(user);
		loadUserWithTestCredits(savedUser);
		Long savedUserID = savedUser.getID();
		RegisteredUser registeredUser = registeredUserService.register(savedUser, TestMembershipExpires, savedCreditCard);
        assertNotNull(userService.getUserById(savedUserID)); // Confirm it will STILL show up in userRepository with same ID
		Long savedRegisteredUserID = registeredUser.getID();
        assertNotNull(registeredUser.getID());
        assertEquals(TestBankName, registeredUser.getCreditCard().getBank().getTitle()); // utilizing getters from base class
        assertEquals(savedUserID, savedRegisteredUserID); // The registeredUser should have the same ID.
        
        List<Credit> registeredCredits = registeredUser.getCredits(); // Make sure foreign keys for credits still work.
        assertEquals(registeredCredits.size(), TestCreditsSize); // Make sure foreign keys for credits still work.
        
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
			Credit savedCredit = creditService.createCredit(credit);
		}
	}

}
