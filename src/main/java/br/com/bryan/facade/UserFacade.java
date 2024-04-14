package br.com.bryan.facade;

import br.com.bryan.model.User;

public interface UserFacade {
    User findById(Long id);
}
