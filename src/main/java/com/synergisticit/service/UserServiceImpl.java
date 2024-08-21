package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired UserRepository userRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(long userId) {
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isPresent()) {
		return optUser.get();
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteById(long userId) {
		userRepository.deleteById(userId);

	}

	@Override
	public boolean existsById(long userId) {
		return userRepository.existsById(userId);
	}

	@Override
	public User findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
    public User findByName(String username) {
        String sql = "SELECT * FROM user WHERE username = :username";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("userId"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            // Map other fields here
            return user;
        });
    }
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    
    @Override
	public List<User> findAll(String sortBy) {
		// TODO Auto-generated method stub
		return userRepository.findAll(Sort.by(sortBy));
	}
    
    

}
