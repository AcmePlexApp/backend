package ENSF614Group1.ACME.Repository;

import ENSF614Group1.ACME.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
