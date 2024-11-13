package ENSF614Group1.ACME;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ENSF614Group1.ACME.Model.*;
import ENSF614Group1.ACME.Repository.*;

@SpringBootTest
//@Transactional // Reverts changes after each test.
class AcmeApplicationTests {
	
//    @Autowired
//    private BankRepository bankRepository;
    
    @Autowired
    private CreditCardRepository creditCardRepository;

	@Test
	void contextLoads() {
		// Leave this in - just checks whether the context loads properly.
	}
	
	@Test
    void testCreateBank() {
		String testBankName = "TestBank";
		Bank bank = new Bank();
		bank.setTitle(testBankName);
		// Do not need to save BANK at this time - CascadeALL will save it
		//Bank savedBank = bankRepository.save(bank);
		
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("1111222233334444");
        creditCard.setFirstName("Test_FirstName");
        creditCard.setLastName("Test_LastName");
        creditCard.setExpiry("1122");
        creditCard.setBank(bank);

        CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        
        assertNotNull(savedCreditCard.getID());
        assertEquals(testBankName, savedCreditCard.getBank().getTitle());
    }
	

}
