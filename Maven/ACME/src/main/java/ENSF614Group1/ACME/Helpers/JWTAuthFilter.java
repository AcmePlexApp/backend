package ENSF614Group1.ACME.Helpers;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import ENSF614Group1.ACME.Service.CustomUserDetailsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired private JWTUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;
    
    public CustomUserDetailsService getUserDetailsService() {return userDetailsService;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
    	
        String token = jwtUtil.getAuthToken(request);
        if (token == null){ // No AuthHeader
        	chain.doFilter(request, response);
        } else {
        	try {
            	String username = jwtUtil.extractUsername(token);
            	jwtUtil.validateToken(token);
            	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                		userDetails, null, userDetails.getAuthorities());
            	authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                chain.doFilter(request, response);
            } catch (Exception e) {
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + e.getLocalizedMessage() + "\"}");
            }
        }
    }
}
