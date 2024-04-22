package br.com.bryan.actions.user;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class EditUserAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private User user;
	private UserFacade userFacade;
	private Map<String, Object> sessionMap;
	
	public EditUserAction() {
		try {
			InitialContext ic = new InitialContext();
			userFacade = (UserFacade) ic.lookup("java:app/health-hub/UserFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up UserFacade", e);
		}
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				if(id == null) {
					addActionError("ID is required.");
					return ERROR;
				}
				
				user = userFacade.findById(id);
				
				if(user == null) {
					addActionError("User not found.");
					return ERROR;
				}
				
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
