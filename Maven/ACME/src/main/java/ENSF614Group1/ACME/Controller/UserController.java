package ENSF614Group1.ACME.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ENSF614Group1.ACME.Helpers.JWTUtil;
import ENSF614Group1.ACME.Model.CreditCard;
import ENSF614Group1.ACME.Model.RegisteredUser;
import ENSF614Group1.ACME.Model.Ticket;
import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired private UserService userService;
	
    @Autowired private JWTUtil jwtUtil;
	
	@GetMapping
	public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authHeader) {
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authHeader){
			String token = jwtUtil.getAuthToken(authHeader);
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			userService.deleteUser(user.getID());
			jwtUtil.blacklistToken(token);
			return ResponseEntity.status(HttpStatus.OK).build();
	}	
	
	@PostMapping("/purchase/{amount}/{applyCredits}")
	public ResponseEntity<String> purchase(@RequestHeader("Authorization") String authHeader, @RequestBody(required = false) CreditCard creditCard, @PathVariable Double amount, @PathVariable Boolean applyCredits){
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			userService.purchase(user.getID(), creditCard, amount, applyCredits);
			String response = amount + " has been charged by " + user.getUsername() + ".";
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/refund/{amount}")
	public ResponseEntity<String> refund(@RequestHeader("Authorization") String authHeader, @RequestBody(required = false) CreditCard creditCard, @PathVariable Double amount){
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			userService.refund(user.getID(), creditCard, amount);
			String response = amount + " has been refunded to " + user.getUsername() + ".";
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestHeader("Authorization") String authHeader, @RequestBody CreditCard creditCard){
			String username = jwtUtil.getUsername(authHeader);
			User user = userService.loadByUsername(username);
			RegisteredUser registeredUser = userService.register(user.getID(), creditCard);
			return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
	}
	
	@PostMapping("/selectseat/{seatId}")
	public ResponseEntity<String> addTicketToCart(@RequestHeader("Authorization") String authHeader, @PathVariable Long seatId){
		String username = jwtUtil.getUsername(authHeader);
		User user = userService.loadByUsername(username);
		userService.addTicketToCart(user.getID(), seatId);
		return ResponseEntity.status(HttpStatus.OK).body("Ticket added to cart.");
	}
	
	 @DeleteMapping("/removeticket/{ticketId}")
	 public ResponseEntity<String> removeTicketFromCart(@RequestHeader("Authorization") String authHeader, @PathVariable Long ticketId){
		 String username = jwtUtil.getUsername(authHeader);
		 User user = userService.loadByUsername(username);
		 userService.removeTicketFromCart(user.getID(), ticketId);
		 return ResponseEntity.status(HttpStatus.OK).body("Ticket removed from cart.");
	 }
	 
	 @GetMapping("/cart")
	 public ResponseEntity<List<Ticket>> getCart(@RequestHeader("Authorization") String authHeader){
		 String username = jwtUtil.getUsername(authHeader);
		 User user = userService.loadByUsername(username);
		 List<Ticket> tickets = userService.getCart(user.getID());
		 return ResponseEntity.status(HttpStatus.OK).body(tickets);
	 }
	 
	 @PostMapping("/cart/purchase/{applyCredits}")
	 public ResponseEntity<String> purchaseTicketsInCart(@RequestHeader("Authorization") String authHeader, 
			 @RequestBody(required = false) CreditCard creditCard, 
			 @PathVariable boolean applyCredits){
		 String username = jwtUtil.getUsername(authHeader);
		 User user = userService.loadByUsername(username);
		 userService.purchaseTicketsInCart(user.getID(), creditCard, applyCredits);
		 return ResponseEntity.status(HttpStatus.OK).body("Ticket(s) in cart purchased.");
	 }
	 
	 @GetMapping("/tickets")
	 public ResponseEntity<List<Ticket>> getUserTickets(@RequestHeader("Authorization") String authHeader){
		 String username = jwtUtil.getUsername(authHeader);
		 User user = userService.loadByUsername(username);
		 List<Ticket> tickets = userService.getUserTickets(user.getID());
		 return ResponseEntity.status(HttpStatus.OK).body(tickets);
	 }
	 
	 @DeleteMapping("/cancelticket/{ticketId}")
	 public ResponseEntity<String> cancelPurchasedTicket(@RequestHeader("Authorization") String authHeader, 
			 @PathVariable Long ticketId, 
			 @RequestBody(required = false) CreditCard creditCard){
		 String username = jwtUtil.getUsername(authHeader);
		 User user = userService.loadByUsername(username);
		 userService.cancelPurchasedTicket(user.getID(), ticketId, creditCard);
		 return ResponseEntity.status(HttpStatus.OK).body("Ticket successfully cancelled.");
	 }
	
}
