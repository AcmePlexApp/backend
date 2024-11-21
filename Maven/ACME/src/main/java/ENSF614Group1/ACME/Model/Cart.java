package ENSF614Group1.ACME.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart")
	private List<Ticket> tickets = new ArrayList<>();
	
	public Long getId() {return id;}
	public User getUser() {return user;}
	public List<Ticket> getTickets(){return tickets;}
	
	public void setUser(User user) {this.user = user;}
	public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
	
	public Cart() {}
	public Cart(User user) {
		this.user = user;
	}
}
