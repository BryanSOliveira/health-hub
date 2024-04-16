package br.com.bryan.facade.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.UserBean;
import br.com.bryan.exceptions.UserAlreadyExistsException;
import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

@Stateless
public class UserFacadeImpl implements UserFacade {

	@EJB
    private UserBean userBean;

	@Override
    public User findById(Long id) {
        return userBean.findById(id);
    }

	@Override
	public void save(User user) throws ValidationException, NoSuchAlgorithmException, UserAlreadyExistsException {
		String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ValidationException("Invalid username.");
        } else if (user.getUsername().length() < 5) {
        	throw new ValidationException("Username must be at least 5 characters long.");
        }
		
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("Invalid password.");
        } else if (user.getPassword().length() < 8) {
        	throw new ValidationException("Password must be at least 8 characters long.");
        }
        
        if (userBean.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with this username already exists.");
        }
        
		userBean.save(user);
	}

	private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

	@Override
	public boolean authenticate(String username, String password) throws NoSuchAlgorithmException {
		User user = userBean.findByUsername(username);
        if (user != null && user.getPassword().equals(encryptPassword(password))) {
            return true;
        }
        return false; 
	}
}
