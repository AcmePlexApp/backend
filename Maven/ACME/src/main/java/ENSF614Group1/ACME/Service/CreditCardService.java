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
public class CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Transactional
	public CreditCard createCreditCard(CreditCard creditCard) {
		return creditCardRepository.save(creditCard);
	}
	
	public List<CreditCard> getAllCreditCards(){
		return creditCardRepository.findAll();
	}
	
	public CreditCard getCreditCardById(Long id){
		Optional<CreditCard> creditCard = creditCardRepository.findById(id);
		if(creditCard.isEmpty()) {
			throw new EntityNotFoundException("CreditCard does not exist.");
		}
		return creditCard.get();
	}

	
	@Transactional
	public CreditCard updateCreditCard(Long id, CreditCard creditCardDetails) {
		Optional<CreditCard> creditCard = creditCardRepository.findById(id);
		if (creditCard.isEmpty()) {
			throw new EntityNotFoundException("CreditCard does not exist.");
		}
		CreditCard s = creditCard.get();
		
		s.setFirstName(creditCardDetails.getFirstName());
		s.setLastName(creditCardDetails.getLastName());
		s.setCardNumber(creditCardDetails.getCardNumber());
		s.setExpiry(creditCardDetails.getExpiry());
		s.setBank(creditCardDetails.getBank());

		return creditCardRepository.save(s);
	}
	
	@Transactional
	public void deleteCreditCard(Long id) {
		if (!creditCardRepository.existsById(id)) {
			throw new EntityNotFoundException("CreditCard does not exist.");
		}
		creditCardRepository.deleteById(id);
	}
    
}