package br.com.bryan.dao;

import java.util.List;

import br.com.bryan.model.User;

public interface UserDAO {
	User findById(Long id);
	
	User findByUsername(String username);

	void save(User user);

	void update(User user);

	void delete(Long id);

	List<User> findAll();
}