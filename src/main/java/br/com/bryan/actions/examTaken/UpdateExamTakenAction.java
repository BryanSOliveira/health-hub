package br.com.bryan.actions.examTaken;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.ExamTaken;

public class UpdateExamTakenAction extends ActionSupport implements SessionAware {

private static final long serialVersionUID = 1L;
	
	private ExamTakenFacade examTakenFacade;
	private ExamTaken examTaken;
	private Map<String, Object> sessionMap;
	
	public UpdateExamTakenAction() {
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
				examTakenFacade.update(examTaken);
				addActionMessage("Exam Taken updated successfully!");
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}
	
	public void validate() {
		if (examTaken.getEmployee() == null || examTaken.getEmployee().getId() == null) {
			addFieldError("examTaken.employee.id","Invalid employee id.");
	    } else if (examTaken.getExam() == null || examTaken.getExam().getId() == null) {
			addFieldError("examTaken.exam.id", "Invalid exam id.");
	    } else if (examTaken.getDate() == null) {
	        addFieldError("examTaken.date", "Invalid date.");
	    }
	}

	public ExamTaken getExamTaken() {
		return examTaken;
	}

	public void setExamTaken(ExamTaken examTaken) {
		this.examTaken = examTaken;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
}
