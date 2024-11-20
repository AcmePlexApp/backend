package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ENSF614Group1.ACME.Model.Cart;
import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	public Cart getUserCart(User user) {
		return cartRepository.findByUser(user);
	}

}
