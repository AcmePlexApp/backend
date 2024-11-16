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
public class CreditService {

	@Autowired
	private CreditRepository creditRepository;
	
	@Transactional
	public Credit createCredit(Credit credit) {
		return creditRepository.save(credit);
	}
	
	public List<Credit> getAllCredits(){
		return creditRepository.findAll();
	}
	
	public Credit getCreditById(Long id){
		Optional<Credit> credit = creditRepository.findById(id);
		if(credit.isEmpty()) {
			throw new EntityNotFoundException("Credit does not exist.");
		}
		return credit.get();
	}

	
	@Transactional
	public Credit updateCredit(Long id, Credit creditDetails) {
		Optional<Credit> credit = creditRepository.findById(id);
		if (credit.isEmpty()) {
			throw new EntityNotFoundException("Credit does not exist.");
		}
		Credit s = credit.get();
		s.setAmount(creditDetails.getAmount());
		s.setAmountUsed(creditDetails.getAmountUsed());
		s.setExpires(creditDetails.getExpires());
		return creditRepository.save(s);
	}
	
	@Transactional
	public void deleteCredit(Long id) {
		if (!creditRepository.existsById(id)) {
			throw new EntityNotFoundException("Credit does not exist.");
		}
		creditRepository.deleteById(id);
	}
    
}