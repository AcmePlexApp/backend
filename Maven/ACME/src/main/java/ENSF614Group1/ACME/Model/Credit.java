package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Double amountUsed;
    private LocalDateTime expires;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public Double getAmount() {return amount;}
    public Double getAmountUsed() {return amountUsed;}
    public LocalDateTime getExpires() {return expires;}
    
    public void setAmount(Double amount) {this.amount = amount;}
    public void setAmountUsed(Double amountUsed) {this.amountUsed = amountUsed;}
    public void setExpires(LocalDateTime expires) {this.expires = expires;}
    
}
