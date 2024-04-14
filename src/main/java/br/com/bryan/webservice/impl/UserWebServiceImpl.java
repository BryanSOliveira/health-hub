package br.com.bryan.webservice.impl;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;
import br.com.bryan.webservice.UserWebService;

@WebService(endpointInterface = "br.com.bryan.webservice.UserWebService")
@WebServlet("/soap/UserService")
public class UserWebServiceImpl implements UserWebService {

	@EJB
	private UserFacade userFacade;
	
	@Override
	public User findUserById(Long id) {
		return userFacade.findById(id);
	}
}
