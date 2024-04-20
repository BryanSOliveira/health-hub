package br.com.bryan.actions.examTaken;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.criteria.SearchCriteria;

public class DeleteExamTakenAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private ExamTakenFacade examTakenFacade;
	private Long id;
	private Map<String, Object> sessionMap;
	
	SearchCriteria criteria = new SearchCriteria();
	
	public DeleteExamTakenAction() {
		try {
			InitialContext ic = new InitialContext();
			examTakenFacade = (ExamTakenFacade) ic.lookup("java:app/health-hub/ExamTakenFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up ExamTakenFacade", e);
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
				
				examTakenFacade.delete(id);
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
