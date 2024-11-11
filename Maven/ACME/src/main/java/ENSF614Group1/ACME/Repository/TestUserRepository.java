package ENSF614Group1.ACME.Repository;

import ENSF614Group1.ACME.Model.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<TestUser, Long> {
}
