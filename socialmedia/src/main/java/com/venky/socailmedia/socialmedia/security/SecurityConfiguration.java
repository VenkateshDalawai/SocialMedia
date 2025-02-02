package com.venky.socailmedia.socialmedia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private MyuserDetail myuserDetail;
	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.cors().and().csrf(customizer -> customizer.disable()).authorizeHttpRequests(register -> {
			register.requestMatchers("/register/user").permitAll();
			register.requestMatchers(
	                "/swagger-ui.html",
	                "/swagger-ui/**",
	                "/v2/api-docs",
	                "/webjars/**",
	                "/swagger-resources/**"
	            ).permitAll();
			register.requestMatchers("/authentication/**").permitAll();
			register.requestMatchers("/StudentData/**").hasRole("ADMIN").anyRequest().authenticated().and()
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		}).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()).build();z
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return myuserDetail;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myuserDetail);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
