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
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;
	
	@Transactional
	public Receipt createReceipt(Receipt receipt) {
		return receiptRepository.save(receipt);
	}
	
	public List<Receipt> getAllReceipts(){
		return receiptRepository.findAll();
	}
	
	public Receipt getReceiptById(Long id){
		Optional<Receipt> receipt = receiptRepository.findById(id);
		if(receipt.isEmpty()) {
			throw new EntityNotFoundException("Receipt does not exist.");
		}
		return receipt.get();
	}

	
	@Transactional
	public Receipt updateReceipt(Long id, Receipt receiptDetails) {
		Optional<Receipt> receipt = receiptRepository.findById(id);
		if (receipt.isEmpty()) {
			throw new EntityNotFoundException("Receipt does not exist.");
		}
		Receipt s = receipt.get();
		s.setTitle(receiptDetails.getTitle());
		return receiptRepository.save(s);
	}
	
	@Transactional
	public void deleteReceipt(Long id) {
		if (!receiptRepository.existsById(id)) {
			throw new EntityNotFoundException("Receipt does not exist.");
		}
		receiptRepository.deleteById(id);
	}
    
}