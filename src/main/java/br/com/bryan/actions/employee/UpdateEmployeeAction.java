package br.com.bryan.actions.employee;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.EmployeeFacade;
import br.com.bryan.model.Employee;

public class UpdateEmployeeAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private EmployeeFacade employeeFacade;
	private Employee employee;
	private Map<String, Object> sessionMap;
	
	public UpdateEmployeeAction() {
		try {
			InitialContext ic = new InitialContext();
			employeeFacade = (EmployeeFacade) ic.lookup("java:app/health-hub/EmployeeFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up employeeFacade", e);
		}
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
	
	public void validate() {
	    if (employee.getName() == null || employee.getName().trim().isEmpty()) {
	        addFieldError("employee.name", "Name is required.");
	    } else if (employee.getName().length() < 3) {
	        addFieldError("employee.name", "Name must be at least 3 characters long.");
	    }
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				employeeFacade.update(employee);
				addActionMessage("Employee updated successfully!");
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
