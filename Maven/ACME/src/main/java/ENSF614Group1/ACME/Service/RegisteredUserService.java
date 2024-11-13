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
public class RegisteredUserService {

	@Autowired
    private RegisteredUserRepository registeredUserRepository;
    
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public RegisteredUser createRegisteredUser(RegisteredUser user) {
        return registeredUserRepository.save(user);
    }

    public Optional<RegisteredUser> getRegisteredUserById(Long id) {
        return registeredUserRepository.findById(id);
    }

    public List<RegisteredUser> getAllRegisteredUsers() {
        return registeredUserRepository.findAll();
    }

    @Transactional
    public void deleteRegisteredUser(Long id) {
        registeredUserRepository.deleteById(id);
    }
    
    @Transactional
    public RegisteredUser register(User user, LocalDateTime membershipExpires, CreditCard creditCard) {
		// Specialization of a Generalized class requires manual SQL commands.
		Long tempID = user.getID();
		entityManager.createNativeQuery("UPDATE user SET user_type = :newType WHERE id = :userId")
    	.setParameter("newType", RegisteredUser.RegisteredUserKey)
    	.setParameter("userId", tempID)
    	.executeUpdate();
		// Flush and clear to reload repository data
		entityManager.flush();
		entityManager.clear();
		RegisteredUser registeredUser = registeredUserRepository.findById(tempID).get();
		registeredUser.setMembershipExpires(membershipExpires);
		registeredUser.setCreditCard(creditCard);
		return registeredUser;
	}
}