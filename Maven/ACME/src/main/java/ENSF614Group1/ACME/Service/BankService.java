package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ENSF614Group1.ACME.Repository.*;
import jakarta.persistence.EntityManager;
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

    public Optional<Bank> getBankById(Long id) {
        return bankRepository.findById(id);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Transactional
    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }
    
}