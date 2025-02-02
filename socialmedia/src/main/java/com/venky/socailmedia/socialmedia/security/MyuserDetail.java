package com.venky.socailmedia.socialmedia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.venky.socailmedia.socialmedia.dao.UserRepo;
import com.venky.socailmedia.socialmedia.entity.Myuser;

import java.util.Optional;

@Service
public class MyuserDetail implements UserDetailsService {

	@Autowired
	private UserRepo userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Myuser> user = userrepo.findByUserName(username);
		System.out.println(user.get().getUserName());
		if (user.isPresent()) {
			var userObj = user.get();
			return User.builder().username(userObj.getUserName()).password(userObj.getPassword())
					.roles(getRole(userObj.getRole())).build();
		} else {
			throw new UsernameNotFoundException(username);
		}

	}

	private String[] getRole(String role) {
		if (role == null) {
			return new String[] { "USER" };
		} else {
			return role.split(",");
		}
	}
}
