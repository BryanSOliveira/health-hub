package br.com.bryan.actions.exam;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamFacade;
import br.com.bryan.model.Exam;

public class CreateExamAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private ExamFacade examFacade;
	private Exam exam;
	private Map<String, Object> sessionMap;
	
	public CreateExamAction() {
		try {
			InitialContext ic = new InitialContext();
			examFacade = (ExamFacade) ic.lookup("java:app/health-hub/ExamFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up ExamFacade", e);
		}
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
	
	public void validate() {
	    if (exam.getName() == null || exam.getName().trim().isEmpty()) {
	        addFieldError("exam.name", "Name is required.");
	    } else if (exam.getName().length() < 3) {
	        addFieldError("exam.name", "Name must be at least 3 characters long.");
	    }
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				exam = examFacade.save(exam);
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
