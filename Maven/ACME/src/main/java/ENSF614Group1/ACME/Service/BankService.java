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
public class BankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Transactional
	public Bank createBank(Bank bank) {
		return bankRepository.save(bank);
	}
	
	public List<Bank> getAllBanks(){
		return bankRepository.findAll();
	}
	
	public Bank getBankById(Long id){
		Optional<Bank> bank = bankRepository.findById(id);
		if(bank.isEmpty()) {
			throw new EntityNotFoundException("Bank does not exist.");
		}
		return bank.get();
	}

	
	@Transactional
	public Bank updateBank(Long id, Bank bankDetails) {
		Optional<Bank> bank = bankRepository.findById(id);
		if (bank.isEmpty()) {
			throw new EntityNotFoundException("Bank does not exist.");
		}
		Bank s = bank.get();
		s.setTitle(bankDetails.getTitle());
		return bankRepository.save(s);
	}
	
	@Transactional
	public void deleteBank(Long id) {
		if (!bankRepository.existsById(id)) {
			throw new EntityNotFoundException("Bank does not exist.");
		}
		bankRepository.deleteById(id);
	}
    
}