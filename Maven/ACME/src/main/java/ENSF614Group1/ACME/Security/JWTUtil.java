package ENSF614Group1.ACME.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;


import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "2LX6QFI9G6MDUgs5qnq4mu9cazmb4Qbi";
    private static final long EXPIRATION_TIME = 86400;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    private final Set<String> blacklistedTokens = new HashSet<>();
    
    public String getAuthToken(HttpServletRequest request) {
    	String authHeader = request.getHeader("Authorization");
    	return getAuthToken(authHeader);
    }
    
    public String getAuthToken(String authHeader) {
    	if (authHeader == null) {return null;}
    	if (!authHeader.startsWith("Bearer ")) {return null;}
    	return authHeader.substring(7);
    }

    // Add token to blacklist
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    // Check if token is blacklisted
    private void checkBlacklist(String token) {
        if(blacklistedTokens.contains(token)) {throw new RuntimeException("User logged out");}
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String extractUsername(String token) throws RuntimeException, ExpiredJwtException {
    	return extractUsername(extractClaims(token));
    }

    private String extractUsername(Claims claims) throws RuntimeException {
    	String extracted = claims.getSubject();
    	if (extracted == null) {throw new RuntimeException("Token contained a null username");}
        return extracted;
    }

    public void validateToken(String token) throws RuntimeException, ExpiredJwtException {
    	checkBlacklist(token);
    	Claims claims = extractClaims(token);    	
    	checkTokenExpiry(claims);
    }

	private Claims extractClaims(String token) {
		return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
	}

	private void checkTokenExpiry(Claims claims) throws RuntimeException {
		Date now = new Date();
		Date expiry = claims.getExpiration();
		boolean expired = expiry.before(now);
		long differenceInMillis = now.getTime() - expiry.getTime();
        long secondsBetween = differenceInMillis / 1000;
		if (expired) {throw new RuntimeException("Token is expired by " + secondsBetween);}
	}

}
