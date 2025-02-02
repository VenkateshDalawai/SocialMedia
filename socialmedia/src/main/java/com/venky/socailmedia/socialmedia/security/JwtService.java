package com.venky.socailmedia.socialmedia.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	private final String encodedKey = "0wSvf/V4evmWCQgZUKbVBWrzNbzP1RalrkK+zfuK3xM=";

	public JwtService() throws NoSuchAlgorithmException {
	}

	// Generates the JWT token for the user
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
				.signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(encodedKey)) // Decode the encoded key
				.compact();
	}

	public Long extractUserId(String token) {
		return Jwts.parser().setSigningKey(Base64.getDecoder().decode(encodedKey)) // Decode the encoded key
				.parseClaimsJws(token).getBody().get("userId", Long.class); // Get the user ID from the token
	}

	// Extracts the username from the token
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(Base64.getDecoder().decode(encodedKey)) // Decode the encoded key
				.parseClaimsJws(token).getBody().getSubject();
	}

	// Validates the token for the user
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Checks if the token has expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extracts the expiration date from the token
	private Date extractExpiration(String token) {
		return Jwts.parser().setSigningKey(Base64.getDecoder().decode(encodedKey)) // Decode the encoded key
				.parseClaimsJws(token).getBody().getExpiration();
	}
}
