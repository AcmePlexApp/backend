package ENSF614Group1.ACME.Security;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired private JWTUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        
        System.out.println("TRYING TO USE AUTHHEADER");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        System.out.println("Username:" + username + ", Token:" + token);
        
        System.out.println("Request:\n" + requestToString(request));
        
        
        System.out.println("AuthHeader:" + authHeader);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                System.out.println("TOKEN VALIDATED");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                		userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
    
    public static String requestToString(HttpServletRequest request) throws IOException {
        StringBuilder requestDetails = new StringBuilder();

        // Append request method and URL
        requestDetails.append("Method: ").append(request.getMethod()).append("\n");
        requestDetails.append("Request URL: ").append(request.getRequestURL()).append("\n");

        // Append headers
        requestDetails.append("Headers:\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            requestDetails.append("\t").append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        // Append parameters
        requestDetails.append("Parameters:\n");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            requestDetails.append("\t").append(paramName).append(": ").append(request.getParameter(paramName)).append("\n");
        }

//        // Append body (requires wrapping the request if it's already consumed)
//        if (request instanceof ContentCachingRequestWrapper) {
//            ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
//            String body = new String(cachingRequest.getContentAsByteArray(), request.getCharacterEncoding());
//            requestDetails.append("Body:\n").append(body).append("\n");
//        } else {
//            // Directly read body (may not work if the stream is already consumed)
//            try (BufferedReader reader = request.getReader()) {
//                String body = reader.lines().collect(Collectors.joining(System.lineSeparator()));
//                requestDetails.append("Body:\n").append(body).append("\n");
//            }
//        }

        return requestDetails.toString();
    }
}
