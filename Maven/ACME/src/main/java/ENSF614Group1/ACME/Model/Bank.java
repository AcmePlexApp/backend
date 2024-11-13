package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    
    @OneToOne
    @JoinColumn(name = "creditcard_id", nullable = false)
    private CreditCard creditCard;
    
    public String getTitle() {return title;}
    
    public void setTitle(String title) {this.title = title;}
    
}
