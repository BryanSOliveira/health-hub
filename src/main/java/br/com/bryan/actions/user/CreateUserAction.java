package br.com.bryan.actions.user;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class CreateUserAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private UserFacade userFacade;
	private User user;
	private Map<String, Object> sessionMap;
	private String confirmPassword;
	
	public CreateUserAction() {
		try {
			InitialContext ic = new InitialContext();
			userFacade = (UserFacade) ic.lookup("java:app/health-hub/UserFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up UserFacade", e);
		}
	}
	
	public void validate() {
		if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
	    	addFieldError("user.username", "Invalid username.");
        } else if (user.getUsername().length() < 5) {
        	addFieldError("user.username", "Username must be at least 5 characters long.");
        } else if (userFacade.findByUsername(user.getUsername()) != null) {
			addFieldError("user.username", "User with this username already exists.");
        }
		
		if (user.getInactiveTime() == null) {
			addFieldError("user.inactiveTime", "Invalid inactive time.");
        } else if (user.getInactiveTime() < 1 || user.getInactiveTime() > 90) {
			addFieldError("user.inactiveTime", "Inactive time must be between 1 and 90 minutes.");
	    }
		
		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            addFieldError("user.password", "Password is required.");
        } else if (user.getPassword().length() < 8) {
            addFieldError("user.password", "Password must be at least 8 characters long.");
        } else if (!user.getPassword().equals(confirmPassword)) {
        	addFieldError("user.confirmPassowrd", "Password and confirm password do not match.");
        }
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				user = userFacade.save(user);
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
	
}
