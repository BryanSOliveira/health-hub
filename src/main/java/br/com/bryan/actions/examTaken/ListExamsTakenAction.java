package br.com.bryan.actions.examTaken;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

public class ListExamsTakenAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private ExamTakenFacade examTakenFacade;
	private List<ExamTaken> examsTaken;
	private Map<String, Object> sessionMap;
	
	private SearchCriteria criteria = new SearchCriteria();
	
	public ListExamsTakenAction() {
		try {
			InitialContext ic = new InitialContext();
			examTakenFacade = (ExamTakenFacade) ic.lookup("java:app/health-hub/ExamTakenFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up ExamTakenFacade", e);
		}
	}

	public List<ExamTaken> getExamsTaken() {
		return examsTaken;
	}

	public void setExamsTaken(List<ExamTaken> examsTaken) {
		this.examsTaken = examsTaken;
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
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				examsTaken = examTakenFacade.findAll(criteria);
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}
	
}
