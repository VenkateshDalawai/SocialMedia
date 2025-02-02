package com.venky.socailmedia.socialmedia.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;

	@Autowired
	private MyuserDetail userDetailsService;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    String path = request.getRequestURI();
	    return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs");
	}

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		String username = null;
		Long userId = null;

		// Check if the request contains a valid JWT token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7); // Remove "Bearer " prefix
			try {
				username = jwtService.extractUsername(jwtToken);
				userId = jwtService.extractUserId(jwtToken); // Extract user ID from the JWT token
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			} catch (MalformedJwtException e) {
				System.out.print(e.getMessage());
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Authenticate the user if JWT is valid
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtService.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Add the user ID to the SecurityContext
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				// You can use this `userId` to set it in the SecurityContext if needed
				// For example, you could store it as an attribute
				request.setAttribute("userId", userId); // Alternatively, you can store it directly in request
			}
		}

		filterChain.doFilter(request, response);
	}
}
