<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | User Detail</title>
<!-- Bootstrap -->
<%@ include file="/WEB-INF/includes/bootstrap.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/includes/header.jspf"%>
	<s:if test="#session.LOGGED_IN_USER != null">
		<div class="container mt-3">
			<div class="card">
				<div class="card-header" style="background-color: #26e2f8;">
					<h2 class="card-title">
						<a class="btn btn-secondary" href="users">
							<i class="bi bi-arrow-90deg-left"> Back</i>
						</a>
						<i class="bi bi-person-lock"></i>
						User
						<s:if test="user != null && user.id != null">
							<span class="badge bg-light text-dark">
								<s:property value="user.id" />
							</span>
							<s:form action="deleteUser" class="d-inline">
								<s:hidden name="id" value="%{user.id}"/>
						    	<button type="submit" class="btn btn-danger float-none float-sm-end" 
						    					onclick="return confirm('Are you sure you want to delete this user?');">
						    		<i class="bi bi-trash3"></i>
						    	</button>
				    		</s:form>
						</s:if>
					</h2>
				</div>
				<s:if test="user != null && user.id != null">
					<div class="card-body">
						<s:if test="hasFieldErrors()">
							<div class="alert alert-danger" role="alert">
							  <s:fielderror />
							</div>
						</s:if>
						<s:form action="updateUser">
							<s:hidden name="user.id" value="%{user.id}" />
							<div class="row g-3">
								<div class="col-sm-6">
							    <label for="userName" class="form-label">Name <span title="Required field" class="text-danger">*</span></label>
							    <s:textfield name="user.username" cssClass="form-control" id="userName" />
							  </div>
							  <div class="col-sm-6">
							    <label for="userInactiveTime" class="form-label">Inactive Time <span title="Required field" class="text-danger">*</span></label>
							    <s:textfield name="user.inactiveTime" 
							    						 cssClass="form-control" 
							    						 id="userInactiveTime" 
							    						 type="number" 
							    						 min="1" 
							    						 max="60" 
							    						 step="1"
							    						 placeholder="in minutes"/>
							  </div>
							</div>
						  <button type="submit" class="btn btn-primary mt-2">Save</button>
						</s:form>
						<div class="card mt-4">
							<div class="card-header">
								<h3 class="card-title"><i class="bi bi-lock"></i> Change Password</h3>
							</div>
							<div class="card-body">
								<s:form action="changePassword">
									<s:hidden name="userId" value="%{user.id}" />
									<div class="row g-3 mt-2">
										<div class="col-sm-6">
											<label for="userNewPassword" class="form-label">Password <span title="Required field" class="text-danger">*</span></label>
									    <s:password cssClass="form-control" id="userNewPassword" name="newPassword" maxlength="50" minlength="8"/>
										</div>
										<div class="col-sm-6">
											<label for="userConfirmNewPassword" class="form-label">Confirm Password <span title="Required field" class="text-danger">*</span></label>
									    <s:password cssClass="form-control" id="userConfirmNewPassword" name="confirmNewPassword" maxlength="50" minlength="8"/>
										</div>
									</div>
								  <s:submit value="Change" cssClass="btn btn-dark mt-2"/>
								</s:form>
							</div>
						</div>
					</div>
				</s:if>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>