package br.com.bryan.actions;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class LoginAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	
	private User loginInfo;
    private UserFacade userFacade;
    private Map<String, Object> sessionMap;
    
    public LoginAction() {
		try {
			InitialContext ic = new InitialContext();
			userFacade = (UserFacade) ic.lookup("java:app/health-hub/UserFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up UserFacade", e);
		}
	}

    public User getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(User loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void validate() {
        if (loginInfo.getUsername() == null || loginInfo.getUsername().trim().isEmpty()) {
            addFieldError("loginInfo.username", "Username is required.");
        } else if (loginInfo.getUsername().length() < 5) {
            addFieldError("loginInfo.username", "Username must be at least 5 characters long.");
        }

        if (loginInfo.getPassword() == null || loginInfo.getPassword().trim().isEmpty()) {
            addFieldError("loginInfo.password", "Password is required.");
        } else if (loginInfo.getPassword().length() < 8) {
            addFieldError("loginInfo.password", "Password must be at least 8 characters long.");
        }
    }

    public String execute() throws NoSuchAlgorithmException {
        if (userFacade.authenticate(loginInfo.getUsername(), loginInfo.getPassword())) {
        	sessionMap.put("LOGGED_IN_USER", loginInfo.getUsername());
            return SUCCESS;
        } else {
            addActionError("Invalid username or password.");
            return ERROR;
        }
    }

	@Override
	public void withSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
}