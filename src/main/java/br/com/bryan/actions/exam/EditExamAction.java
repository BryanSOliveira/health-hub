package br.com.bryan.actions.exam;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamFacade;
import br.com.bryan.model.Exam;

public class EditExamAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Exam exam;
	private ExamFacade examFacade;
	private Map<String, Object> sessionMap;
	
	public EditExamAction() {
		try {
			InitialContext ic = new InitialContext();
			examFacade = (ExamFacade) ic.lookup("java:app/health-hub/ExamFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up ExamFacade", e);
		}
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Exam getExam() {
		return exam;
	}
	
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	@Override
	public void withSession(Map<String, Object> session) {
		this.sessionMap = session;
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				if(id == null) {
					addActionError("ID is required.");
					return ERROR;
				}
				
				exam = examFacade.findById(id);
				
				if(exam == null) {
					addActionError("Exam not found.");
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
}
