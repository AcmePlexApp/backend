package ENSF614Group1.ACME.Controller;

import ENSF614Group1.ACME.Model.Credit;
import ENSF614Group1.ACME.Repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private CreditRepository creditRepository;

    // Get all credits
    @GetMapping
    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    // Create a new credit
    @PostMapping
    public Credit createCredit(@RequestBody Credit credit) {
        return creditRepository.save(credit);
    }

    // Get a credit by ID
    @GetMapping("/{id}")
    public Credit getCreditById(@PathVariable Long id) {
        return creditRepository.findById(id).orElse(null);
    }

    // Update a credit by ID
    @PutMapping("/{id}")
    public Credit updateCredit(@PathVariable Long id, @RequestBody Credit creditDetails) {
    	Credit credit = creditRepository.findById(id).orElse(null);
        if (credit != null) {
            
            credit.setAmount(creditDetails.getAmount());
            credit.setAmountUsed(creditDetails.getAmountUsed());
            credit.setExpires(creditDetails.getExpires());
            return creditRepository.save(credit);
        }
        return null;
    }

    // Delete a credit by ID
    @DeleteMapping("/{id}")
    public void deleteCredit(@PathVariable Long id) {
    	creditRepository.deleteById(id);
    }
}
