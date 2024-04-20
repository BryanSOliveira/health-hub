package br.com.bryan.actions.employee;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.EmployeeFacade;
import br.com.bryan.model.Employee;

public class EditEmployeeAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Employee employee;
	private EmployeeFacade employeeFacade;
	private Map<String, Object> sessionMap;
	
	public EditEmployeeAction() {
		try {
			InitialContext ic = new InitialContext();
			employeeFacade = (EmployeeFacade) ic.lookup("java:app/health-hub/EmployeeFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up employeeFacade", e);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				if(id == null) {
					addActionError("ID is required.");
					return ERROR;
				}
				
				employee = employeeFacade.findById(id);
				
				if(employee == null) {
					addActionError("Employee not found.");
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
