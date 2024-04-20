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

public class EditExamTakenAction extends ActionSupport implements SessionAware {

private static final long serialVersionUID = 1L;
	
	private Long id;
	private ExamTaken examTaken;
	private ExamTakenFacade examTakenFacade;
	private Map<String, Object> sessionMap;
	
	private ExamFacade examFacade;
	private EmployeeFacade employeeFacade;
	private List<Exam> exams;
	private List<Employee> employees;
	
	public EditExamTakenAction() {
		try {
			InitialContext ic = new InitialContext();
			examTakenFacade = (ExamTakenFacade) ic.lookup("java:app/health-hub/ExamTakenFacadeImpl");
			examFacade = (ExamFacade) ic.lookup("java:app/health-hub/ExamFacadeImpl");
			employeeFacade = (EmployeeFacade) ic.lookup("java:app/health-hub/EmployeeFacadeImpl");
		} catch (NamingException e) {
			throw new RuntimeException("Failed to look up facades", e);
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
				
				examTaken = examTakenFacade.findById(id);
				
				if(examTaken == null) {
					addActionError("Exam Taken not found.");
					return ERROR;
				}
				
				employees = employeeFacade.findAll(null);
				exams = examFacade.findAll(new SearchCriteria("active"));
				
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
