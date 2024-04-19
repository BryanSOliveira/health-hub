package br.com.bryan.actions;

import java.util.Map;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> sessionMap;
	
	@Override
	public void withSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

    public String execute() {
    	try {
	    	if (sessionMap != null) {
	            sessionMap.clear();
	        }
	        return SUCCESS;
    	} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
    }
}
