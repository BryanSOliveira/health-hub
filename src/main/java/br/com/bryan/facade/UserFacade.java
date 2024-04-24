package br.com.bryan.facade;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.exceptions.EntityNotFoundException;
import br.com.bryan.exceptions.UserAlreadyExistsException;
import br.com.bryan.model.User;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface UserFacade {
	User findById(Long id) throws EntityNotFoundException;
	
	User findByUsername(String username);
	
	User save(User user) throws ValidationException, NoSuchAlgorithmException, UserAlreadyExistsException;
	
    void update(User user) throws ValidationException, UserAlreadyExistsException, EntityNotFoundException;
    
    void delete(Long id) throws EntityNotFoundException;
    
    List<User> findAll(SearchCriteria criteria);
	
    User authenticate(String username, String password) throws NoSuchAlgorithmException;
	
	boolean isUsernameAvailable(Long id, String username);
	
	void updatePassword(Long userId, String newPassword) throws ValidationException, NoSuchAlgorithmException;
}
