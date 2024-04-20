package br.com.bryan.actions.employee;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.EmployeeFacade;
import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

public class ListEmployeeAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private EmployeeFacade employeeFacade;
	private List<Employee> employees;
	private Map<String, Object> sessionMap;
	
	private SearchCriteria criteria = new SearchCriteria();
	
	public ListEmployeeAction() {
		try {
			InitialContext ic = new InitialContext();
			employeeFacade = (EmployeeFacade) ic.lookup("java:app/health-hub/EmployeeFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up employeeFacade", e);
		}
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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
				employees = employeeFacade.findAll(criteria);
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
