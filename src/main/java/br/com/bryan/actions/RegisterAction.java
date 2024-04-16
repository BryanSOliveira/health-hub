package br.com.bryan.actions;

import javax.naming.InitialContext;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.exceptions.UserAlreadyExistsException;
import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class RegisterAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private UserFacade userFacade;
	private User registerInfo;

	public RegisterAction() {
		try {
			InitialContext ic = new InitialContext();
			userFacade = (UserFacade) ic.lookup("java:app/health-hub/UserFacadeImpl");
		} catch (Exception e) {
			throw new RuntimeException("Failed to look up UserFacade", e);
		}
	}
	
	public User getRegisterInfo() {
		return registerInfo;
	}

	public void setRegisterInfo(User registerInfo) {
		this.registerInfo = registerInfo;
	}

	public void validate() {
        if (registerInfo.getUsername() == null || registerInfo.getUsername().trim().isEmpty()) {
            addFieldError("registerInfo.username", "Username is required.");
        } else if (registerInfo.getUsername().length() < 5) {
            addFieldError("registerInfo.username", "Username must be at least 5 characters long.");
        }
        
        if (registerInfo.getPassword() == null || registerInfo.getPassword().trim().isEmpty()) {
            addFieldError("registerInfo.password", "Password is required.");
        } else if (registerInfo.getPassword().length() < 8) {
            addFieldError("registerInfo.password", "Password must be at least 8 characters long.");
        }
    }

	public String execute() {
		try {
			userFacade.save(registerInfo);		
			addActionMessage("Registration successful!");
			return SUCCESS;
		} catch (UserAlreadyExistsException e) {
            addActionError(e.getMessage());
            return INPUT;
        } catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}
}
