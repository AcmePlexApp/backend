package ENSF614Group1.ACME.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import ENSF614Group1.ACME.Model.User;
import ENSF614Group1.ACME.Security.JWTUtil;
import ENSF614Group1.ACME.Service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JWTUtil jwtUtil;
    
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");
        try {
            User user = userService.createUser(username, password, email);
            return ResponseEntity.ok().body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
    	
        String username = request.get("username");
        String password = request.get("password");
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userService.loadByUsername(username);
        String token = jwtUtil.generateToken(username);
        
        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = jwtUtil.getAuthToken(authHeader);
    	if (token == null) {
            return ResponseEntity.badRequest().body("Authorization header is missing or invalid.");
        }
        // Invalidate the token by adding it to the blacklist
        jwtUtil.blacklistToken(token);
        return ResponseEntity.ok("Logged out successfully.");
    }
    
}
