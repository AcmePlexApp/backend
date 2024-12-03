package ENSF614Group1.ACME.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ENSF614Group1.ACME.Model.Cart;
import ENSF614Group1.ACME.Model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
