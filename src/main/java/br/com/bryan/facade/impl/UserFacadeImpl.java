package br.com.bryan.facade.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.UserBean;
import br.com.bryan.exceptions.UserAlreadyExistsException;
import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class UserFacadeImpl implements UserFacade {

	@EJB
    private UserBean userBean;

	@Override
    public User findById(Long id) {
        return userBean.findById(id);
    }
	
	@Override
	public User findByUsername(String username) {
		return userBean.findByUsername(username);
	}

	@Override
	public User save(User user) throws ValidationException, NoSuchAlgorithmException, UserAlreadyExistsException {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validateInactiveTime(user.getInactiveTime());
        
        if (userBean.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with this username already exists.");
        }
        
        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        
		return userBean.save(user);
	}
	
	@Override
	public void update(User user) throws ValidationException, UserAlreadyExistsException {
		validateUsername(user.getUsername());
		validateInactiveTime(user.getInactiveTime());
		
		if (!userBean.isUsernameAvailable(user.getId(), user.getUsername())) {
			throw new UserAlreadyExistsException("User with this username already exists.");
		}
		
		userBean.update(user);
	}
	
	@Override
	public void delete(Long id) {
		userBean.delete(id);
	}
	
	@Override
	public List<User> findAll(SearchCriteria criteria) {
		return userBean.findAll(criteria);
	}
	
	@Override
	public boolean isUsernameAvailable(Long id, String username) {
		return userBean.isUsernameAvailable(id, username);
	}

	@Override
	public User authenticate(String username, String password) throws NoSuchAlgorithmException {
		User user = userBean.findByUsername(username);
        if (user != null && user.getPassword().equals(encryptPassword(password))) {
            return null;
        }
        return user; 
	}
	
	@Override
	public void updatePassword(Long userId, String newPassword) throws ValidationException, NoSuchAlgorithmException {
		validatePassword(newPassword);
		
		String encryptedNewPassword = encryptPassword(newPassword);
        
		userBean.updatePassword(userId, encryptedNewPassword);
	}

	private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
	
	public void validateUsername(String username) throws ValidationException {
		if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Invalid username.");
        } else if (username.length() < 5) {
        	throw new ValidationException("Username must be at least 5 characters long.");
        }
	}
	
	public void validatePassword(String password) throws ValidationException {
		if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Invalid password.");
        } else if (password.length() < 8) {
        	throw new ValidationException("Password must be at least 8 characters long.");
        }
	}
	
	public void validateInactiveTime(Integer inactiveTime) throws ValidationException {
		if (inactiveTime == null) {
            throw new ValidationException("Invalid inactive time.");
        }
		
		if (inactiveTime < 1 || inactiveTime > 60) {
	        throw new ValidationException("Inactive time must be between 1 and 60 minutes.");
	    }
	}
	
}
