package com.synergisticit.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Role {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@NotEmpty
	private String roleName;
	
	public Role(Long roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
		
	}
	
	@JsonIgnore  
	@ManyToMany(mappedBy="roles")
	private List<User> users = new ArrayList<>();
	
	

}
