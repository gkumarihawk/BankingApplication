package com.synergisticit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;


public interface RoleService {
            Role save(Role role);
            Role findById(Long roleId);
            List<Role> findAll();
            
            void deleteById(Long roleId);
            
            List<Role> findAll(String sortBy);
            
}
