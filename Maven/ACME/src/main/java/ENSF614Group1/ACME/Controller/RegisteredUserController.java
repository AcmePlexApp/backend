package ENSF614Group1.ACME.Controller;

import ENSF614Group1.ACME.Model.RegisteredUser;
import ENSF614Group1.ACME.Repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/registereduser")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    // Get all registeredUsers
    @GetMapping
    public List<RegisteredUser> getAllRegisteredUsers() {
        return registeredUserRepository.findAll();
    }

    // Create a new registeredUser
    @PostMapping
    public RegisteredUser createRegisteredUser(@RequestBody RegisteredUser registeredUser) {
        return registeredUserRepository.save(registeredUser);
    }

    // Get a registeredUser by ID
    @GetMapping("/{id}")
    public RegisteredUser getRegisteredUserById(@PathVariable Long id) {
        return registeredUserRepository.findById(id).orElse(null);
    }

    // Update a registeredUser by ID
    @PutMapping("/{id}")
    public RegisteredUser updateRegisteredUser(@PathVariable Long id, @RequestBody RegisteredUser registeredUserDetails) {
    	RegisteredUser registeredUser = registeredUserRepository.findById(id).orElse(null);
        if (registeredUser != null) {
            registeredUser.setMembershipExpires(registeredUserDetails.getMembershipExpires());
            registeredUser.setCreditCard(registeredUserDetails.getCreditCard());
            return registeredUserRepository.save(registeredUser);
        }
        return null;
    }

    // Delete a registeredUser by ID
    @DeleteMapping("/{id}")
    public void deleteRegisteredUser(@PathVariable Long id) {
    	registeredUserRepository.deleteById(id);
    }
}
