package ENSF614Group1.ACME.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {throw new UsernameNotFoundException("User not found with username: " + username);}
        
        ArrayList<SimpleGrantedAuthority> roles = user.getRole().isEmpty() ? new ArrayList<SimpleGrantedAuthority>() : new ArrayList<SimpleGrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
        System.out.println(roles.toString());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles // Add roles/authorities if applicable
        );
    }
}