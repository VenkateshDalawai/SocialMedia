package com.venky.socailmedia.socialmedia.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.venky.socailmedia.socialmedia.dao.UserRepo;
import com.venky.socailmedia.socialmedia.entity.Myuser;
import com.venky.socailmedia.socialmedia.security.JwtService;
import com.venky.socailmedia.socialmedia.security.MyuserDetail;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MyuserDetail myuserDetail;

	@PostMapping("/register/user")
	public ResponseEntity<?> createUser(@RequestBody Myuser user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return ResponseEntity.ok().body("Registered Successfully");
	}

	@GetMapping("/user/id")
	public Myuser getUserIdFromToken(@RequestHeader("Authorization") String token) {

		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7); // Remove 'Bearer ' prefix
			String username = jwtService.extractUsername(jwtToken);

			UserDetails userDetails = myuserDetail.loadUserByUsername(username);

			if (userDetails != null) {

				Myuser user = userRepo.findByUserName(username)
						.orElseThrow(() -> new RuntimeException("User not found"));

				return user;
			} else {
				throw new RuntimeException("User not found");
			}
		} else {
			throw new RuntimeException("Invalid token");
		}
	}

}
