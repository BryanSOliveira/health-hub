package br.com.bryan.facade;

import java.security.NoSuchAlgorithmException;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.exceptions.UserAlreadyExistsException;
import br.com.bryan.model.User;

@Local
public interface UserFacade {
    User findById(Long id);
    void save(User user) throws ValidationException, NoSuchAlgorithmException, UserAlreadyExistsException;
	boolean authenticate(String username, String password) throws NoSuchAlgorithmException;
}
