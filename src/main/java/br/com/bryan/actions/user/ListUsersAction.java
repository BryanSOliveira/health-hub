package br.com.bryan.actions.user;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;
import br.com.bryan.model.criteria.SearchCriteria;

public class ListUsersAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private UserFacade userFacade;
	private List<User> users;
	private Map<String, Object> sessionMap;
	
	private SearchCriteria criteria = new SearchCriteria();
	
	public ListUsersAction() {
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
				users = userFacade.findAll(criteria);
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
	
}
