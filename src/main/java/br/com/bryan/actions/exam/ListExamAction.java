package br.com.bryan.actions.exam;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamFacade;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

public class ListExamAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private ExamFacade examFacade;
	private List<Exam> exams;
	private Map<String, Object> sessionMap;
	
	private SearchCriteria criteria = new SearchCriteria();
	
	public ListExamAction() {
		try {
			InitialContext ic = new InitialContext();
			examFacade = (ExamFacade) ic.lookup("java:app/health-hub/ExamFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up ExamFacade", e);
		}
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				exams = examFacade.findAll(criteria);
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
