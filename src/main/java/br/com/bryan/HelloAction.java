package br.com.bryan;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String execute() {
		String userName = userService.getUserName();
		addActionMessage("Hello, " + userName + "!");
		return SUCCESS;
	}
}
