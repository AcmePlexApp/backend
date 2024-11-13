package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    
    @OneToOne(mappedBy = "bank")
	private CreditCard creditCard;
//spring.flyway.enabled=true and spring.flyway.baselineOnMigrate=true
    
    public Long getID() {return id;}
    public String getTitle() {return title;}
    
    public void setTitle(String title) {this.title = title;}
    
}
