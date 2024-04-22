package br.com.bryan.actions.examTaken;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.EmployeeFacade;
import br.com.bryan.facade.ExamFacade;
import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.Employee;
import br.com.bryan.model.Exam;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

public class UpdateExamTakenAction extends ActionSupport implements SessionAware {

private static final long serialVersionUID = 1L;
	
	private ExamTakenFacade examTakenFacade;
	private ExamTaken examTaken;
	private Map<String, Object> sessionMap;
	
	private ExamFacade examFacade;
	private EmployeeFacade employeeFacade;
	private List<Exam> exams;
	private List<Employee> employees;
	
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
			if(isLoggedIn()) {
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
		
		if ((hasActionErrors() || hasFieldErrors()) && isLoggedIn()) {
			loadFormData();
		}
	}
	
	public void loadFormData() {
		try {
			InitialContext ic = new InitialContext();
			examFacade = (ExamFacade) ic.lookup("java:app/health-hub/ExamFacadeImpl");
			employeeFacade = (EmployeeFacade) ic.lookup("java:app/health-hub/EmployeeFacadeImpl");
		} catch (NamingException e) {
			throw new RuntimeException("Failed to look up facades", e);
		}
		
		try {
			employees = employeeFacade.findAll(null);
			exams = examFacade.findAll(new SearchCriteria("active"));
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
		}
	}
	
	public Boolean isLoggedIn() {
		return sessionMap.get("LOGGED_IN_USER") != null;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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
