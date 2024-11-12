package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> posts = new ArrayList<>();
    
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    
    
}
