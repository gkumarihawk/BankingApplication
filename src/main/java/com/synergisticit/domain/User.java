package com.synergisticit.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
	
	@Email
	@NotEmpty
	private String email;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="user_role", joinColumns= {@JoinColumn(name="user_id")}, inverseJoinColumns= {@JoinColumn(name="role_id")})
	List<Role> roles = new ArrayList<>();

}
