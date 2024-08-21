package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired RoleRepository roleRepository;
	
	@Autowired private JdbcTemplate jdbcTemplate;

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role findById(Long roleId) {
		Optional<Role> optRole = roleRepository.findById(roleId);
		if(optRole.isPresent()) {
		return optRole.get();
		}
		return null;
		
	}

	@Override
	public List<Role> findAll() {
		
		return roleRepository.findAll();
	}

	/*@Override
	public void deleteById(Long roleId) {
		roleRepository.deleteById(roleId);

	}*/
	
	@Override
    public void deleteById(Long roleId) {
        String sql = "DELETE FROM role WHERE roleId = ?";
        jdbcTemplate.update(sql, roleId);
    }
	
	@Override
	public List<Role> findAll(String sortBy) {
		// TODO Auto-generated method stub
		return roleRepository.findAll(Sort.by(sortBy));
	}

}
