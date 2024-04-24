<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | New User</title>
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
						<span class="badge bg-light text-dark">NEW</span>
					</h2>
				</div>
				<div class="card-body">
					<s:if test="hasFieldErrors()">
						<div class="alert alert-danger" role="alert">
							<s:fielderror />
						</div>
					</s:if>
					<s:form action="createUser">
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
						    						 max="90" 
						    						 step="1"
						    						 placeholder="in minutes"/>
						  </div>
						</div>
						<div class="row g-3 mt-2">
							<div class="col-sm-6">
								<label for="userPassword" class="form-label">Password <span title="Required field" class="text-danger">*</span></label>
						    <s:password cssClass="form-control" id="userPassword" name="user.password" maxlength="50" minlength="8"/>
							</div>
							<div class="col-sm-6">
								<label for="userConfirmPassword" class="form-label">Confirm Password <span title="Required field" class="text-danger">*</span></label>
						    <s:password cssClass="form-control" id="userConfirmPassword" name="confirmPassword" maxlength="50" minlength="8"/>
							</div>
						</div>
					  <s:submit value="Create" cssClass="btn btn-primary mt-2"/>
					</s:form>
				</div>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>