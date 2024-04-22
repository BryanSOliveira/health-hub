package br.com.bryan.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.bryan.dao.impl.UserDAOImpl;
import br.com.bryan.model.User;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class UserBean {

	@Inject
	private UserDAOImpl userDAOImpl;

	public User findById(Long id) {
		return userDAOImpl.findById(id);
	}
	
	public User save(User user) {
		return userDAOImpl.save(user);
	}
	
	public void update(User user) {
		userDAOImpl.update(user);
	}
	
	public void delete(Long id) {
		userDAOImpl.delete(id);
	}
	
	public List<User> findAll(SearchCriteria criteria) {
		return userDAOImpl.findAll(criteria);
	}
	
	public User findByUsername(String username) {
		return userDAOImpl.findByUsername(username);
	}
	
	public boolean isUsernameAvailable(Long id, String username) {
		return userDAOImpl.isUsernameAvailable(id, username);
	}
	
	public void updatePassword(Long userId, String newPassword) {
		userDAOImpl.updatePassword(userId, newPassword);
	}
	
}
