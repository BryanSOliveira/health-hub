package br.com.bryan.dao;

import java.util.List;

import br.com.bryan.model.User;
import br.com.bryan.model.criteria.SearchCriteria;

public interface UserDAO {
	User findById(Long id);
	
	User findByUsername(String username);

	User save(User user);

	void update(User user);

	void delete(Long id);

	List<User> findAll(SearchCriteria criteria);
	
	boolean isUsernameAvailable(Long id, String username);
	
	void updatePassword(Long userId, String newPassword);
}