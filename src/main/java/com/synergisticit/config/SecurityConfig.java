package com.synergisticit.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
	
	@Autowired AccessDeniedHandler accessDeniedHandler;
    @Autowired AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		List<UserDetails> users = new ArrayList<>();
		
		//1st
		List<GrantedAuthority> authority1 = new ArrayList<>();
		authority1.add(new SimpleGrantedAuthority("Student"));
		
		UserDetails user1 = new User("Gauri2", "{noop}gauri2", authority1);
		users.add(user1);
		
		//2nd
		List<GrantedAuthority> authority2 = new ArrayList<>();
		authority2.add(new SimpleGrantedAuthority("Student"));
		authority2.add(new SimpleGrantedAuthority("Learner"));
		
		UserDetails user2 = new User("Gauri", "{noop}gauri", authority2);
		users.add(user2);
		
		//3rd
		List<GrantedAuthority> authority3 = new ArrayList<>();
		authority3.add(new SimpleGrantedAuthority("Instructor"));
		
		List<GrantedAuthority> authority4 = new ArrayList<>();
		authority4.add(new SimpleGrantedAuthority("Admin"));
		
		List<GrantedAuthority> authority5 = new ArrayList<>();
		authority5.add(new SimpleGrantedAuthority("CEO"));
		
		UserDetails user3 = new User("Dinesh", "{noop}dinesh", authority3);
		users.add(user3);
		
		//4th
		UserDetails user4 = new User("Ashish", "{noop}ashish", authority5);
		users.add(user4);
		
		//5th
		//UserDetails user5 = new User("Dinesh", "{noop}gauri", authority3);
		//users.add(user5);
		
		//6th
		UserDetails user6 = new User("bryan", "{noop}bryan", authority3);
		users.add(user6);
		
		//7th
		UserDetails user7 = new User("danny", "{noop}danny", authority3);
		users.add(user7);
		
		//8th
		UserDetails user8 = new User("garcia", "{noop}garcia", authority3);
		users.add(user8);
		
		//9th
		UserDetails user9 = new User("khan", "{noop}khan", authority3);
		users.add(user9);
		
		UserDetails user10 = new User("Admin", "{noop}admin", authority4);
		users.add(user10);
		
		return new InMemoryUserDetailsManager(users);
		
	}
	
	@Bean
	DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(inMemoryUserDetailsManager());
		return authProvider;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .authorizeHttpRequests()
	            .requestMatchers("/").permitAll()
	            .requestMatchers("/login").permitAll()
	            .requestMatchers("/userForm", "/bankTransactionForm", "/saveBankTransaction", "/saveUser").permitAll()
	            .requestMatchers("/deleteUser", "/branchForm", "/customerForm", "/roleForm", "/accountForm").hasAnyAuthority("Admin")
	            .requestMatchers("/WEB-INF/jsp/**").permitAll()
	            .anyRequest().authenticated()
	        .and()
	        .formLogin()
	            .loginPage("/login")
	            .successHandler(authenticationSuccessHandler)
	        .and()
	        .exceptionHandling()
	            .accessDeniedHandler(accessDeniedHandler);

	    return http.build();
	}
}


