package br.com.bryan.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.bryan.dao.impl.UserDAOImpl;
import br.com.bryan.model.User;

@Stateless
public class UserBean {

	@Inject
	private UserDAOImpl userDAOImpl;

	public User findById(Long id) {
		return userDAOImpl.findById(id);
	}
}
