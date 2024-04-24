package br.com.bryan.actions.user;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class UpdateUserAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private UserFacade userFacade;
	private User user;
	private Map<String, Object> sessionMap;
	
	public UpdateUserAction() {
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
        } else if (!userFacade.isUsernameAvailable(user.getId(), user.getUsername())) {
			addFieldError("user.username", "User with this username already exists.");
        }
		
		if (user.getInactiveTime() == null) {
			addFieldError("user.inactiveTime", "Invalid inactive time.");
        } else if (user.getInactiveTime() < 1 || user.getInactiveTime() > 90) {
			addFieldError("user.inactiveTime", "Inactive time must be between 1 and 90 minutes.");
	    }
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				Long loggedInUserId = (Long) sessionMap.get("LOGGED_IN_USER_ID");
				if (loggedInUserId.equals(user.getId())) {
					addActionError("You cannot edit your own username.");
					return ERROR;
				}
				
				userFacade.update(user);
				addActionMessage("User updated successfully!");
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
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
