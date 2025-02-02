package com.venky.socailmedia.socialmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.venky.socailmedia.socialmedia.entity.Myuser;
import com.venky.socailmedia.socialmedia.security.JwtService;
import com.venky.socailmedia.socialmedia.security.MyuserDetail;

@RestController
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyuserDetail myUserDetail;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/authentication/{userName}/{password}")
	public ResponseEntity<?> authenticateAndGetToken(@PathVariable("userName") String userName,@PathVariable("password") String password) {
		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			final UserDetails userDetails = myUserDetail.loadUserByUsername(userName);
			final String jwt = jwtService.generateToken(userDetails);
			return ResponseEntity.ok(jwt);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
	}
}
