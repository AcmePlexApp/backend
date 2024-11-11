package ENSF614Group1.ACME.Controller;

import ENSF614Group1.ACME.Model.TestUser;
import ENSF614Group1.ACME.Repository.TestUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testusers")
public class TestUserController {

    @Autowired
    private TestUserRepository testUserRepository;

    // Get all users
    @GetMapping
    public List<TestUser> getAllUsers() {
        return testUserRepository.findAll();
    }

    // Create a new user
    @PostMapping
    public TestUser createTestUser(@RequestBody TestUser user) {
        return testUserRepository.save(user);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public TestUser getUserById(@PathVariable Long id) {
        return testUserRepository.findById(id).orElse(null);
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public TestUser updateUser(@PathVariable Long id, @RequestBody TestUser userDetails) {
    	TestUser user = testUserRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return testUserRepository.save(user);
        }
        return null;
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
    	testUserRepository.deleteById(id);
    }
}
