package ENSF614Group1.ACME.Controller;

import ENSF614Group1.ACME.Model.CreditCard;
import ENSF614Group1.ACME.Repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creditcard")
public class CreditCardController {

    @Autowired
    private CreditCardRepository creditCardRepository;

    // Get all creditCards
    @GetMapping
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    // Create a new creditCard
    @PostMapping
    public CreditCard createCreditCard(@RequestBody CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    // Get a creditCard by ID
    @GetMapping("/{id}")
    public CreditCard getCreditCardById(@PathVariable Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    // Update a creditCard by ID
    @PutMapping("/{id}")
    public CreditCard updateCreditCard(@PathVariable Long id, @RequestBody CreditCard creditCardDetails) {
    	CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
        if (creditCard != null) {
            creditCard.setFirstName(creditCardDetails.getFirstName());
            creditCard.setLastName(creditCardDetails.getLastName());
            creditCard.setCardNumber(creditCardDetails.getCardNumber());
            creditCard.setExpiry(creditCardDetails.getExpiry());
            return creditCardRepository.save(creditCard);
        }
        return null;
    }

    // Delete a creditCard by ID
    @DeleteMapping("/{id}")
    public void deleteCreditCard(@PathVariable Long id) {
    	creditCardRepository.deleteById(id);
    }
}
