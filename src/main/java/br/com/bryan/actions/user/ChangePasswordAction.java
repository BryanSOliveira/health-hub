package br.com.bryan.actions.user;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.exceptions.EntityNotFoundException;
import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class ChangePasswordAction extends ActionSupport implements SessionAware {

private static final long serialVersionUID = 1L;
	
	private UserFacade userFacade;
	private Long userId;
	private String newPassword;
	private String confirmNewPassword;
	private Map<String, Object> sessionMap;
	
	private User user;
	
	public ChangePasswordAction() {
		try {
			InitialContext ic = new InitialContext();
			userFacade = (UserFacade) ic.lookup("java:app/health-hub/UserFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up UserFacade", e);
		}
	}
	
	public void validate() {
		if (newPassword == null || newPassword.trim().isEmpty()) {
            addFieldError("newPassword", "Password is required.");
        } else if (newPassword.length() < 8) {
            addFieldError("newPassword", "Password must be at least 8 characters long.");
        } else if (!newPassword.equals(confirmNewPassword)) {
        	addFieldError("confirmNewPassword", "Password and confirm password do not match.");
        }
		
		if ((hasActionErrors() || hasFieldErrors()) && isLoggedIn()) {
			loadEditUser();
		}
	}
	
	public String execute() {
		try {
			if(isLoggedIn()) {
				userFacade.updatePassword(userId, newPassword);
				addActionMessage("Password updated successfully!");
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}
	
	public void loadEditUser() {
		try {
			user = userFacade.findById(userId);
		} catch (EntityNotFoundException e) {
			addActionError("An error occurred: " + e.getMessage());
		}
	}
	
	public boolean isLoggedIn() {
		return sessionMap.get("LOGGED_IN_USER") != null;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
