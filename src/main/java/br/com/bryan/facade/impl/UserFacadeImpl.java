package br.com.bryan.facade.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.bryan.ejb.UserBean;
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
}
