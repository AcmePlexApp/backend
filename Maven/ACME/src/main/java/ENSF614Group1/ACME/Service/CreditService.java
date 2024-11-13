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
public class CreditService {

	@Autowired
    private CreditRepository creditRepository;

    @Transactional
    public Credit createCredit(Credit credit) {
        return creditRepository.save(credit);
    }

    public Optional<Credit> getCreditById(Long id) {
        return creditRepository.findById(id);
    }

    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    @Transactional
    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
    }
    
}