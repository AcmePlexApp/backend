package ENSF614Group1.ACME.Controller;

import ENSF614Group1.ACME.Model.Bank;
import ENSF614Group1.ACME.Repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankRepository bankRepository;

    // Get all banks
    @GetMapping
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    // Create a new bank
    @PostMapping
    public Bank createBank(@RequestBody Bank bank) {
        return bankRepository.save(bank);
    }

    // Get a bank by ID
    @GetMapping("/{id}")
    public Bank getBankById(@PathVariable Long id) {
        return bankRepository.findById(id).orElse(null);
    }

    // Update a bank by ID
    @PutMapping("/{id}")
    public Bank updateBank(@PathVariable Long id, @RequestBody Bank bankDetails) {
    	Bank bank = bankRepository.findById(id).orElse(null);
        if (bank != null) {
            bank.setTitle(bankDetails.getTitle());
            return bankRepository.save(bank);
        }
        return null;
    }

    // Delete a bank by ID
    @DeleteMapping("/{id}")
    public void deleteBank(@PathVariable Long id) {
    	bankRepository.deleteById(id);
    }
}
