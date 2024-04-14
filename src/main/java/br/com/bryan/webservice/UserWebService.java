package br.com.bryan.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.com.bryan.model.User;

@WebService
public interface UserWebService {

	@WebMethod
	User findUserById(Long id);
}
