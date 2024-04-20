<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | New Employee</title>
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
						<a class="btn btn-secondary" href="employees">
							<i class="bi bi-arrow-90deg-left"> Back</i>
						</a>		
						<i class="bi bi-people"></i>
						Employee 
						<span class="badge bg-light text-dark">NEW</span>
					</h2>
				</div>
				<div class="card-body">
					<s:form action="createEmployee">
					  <div class="mb-3">
					  	<s:fielderror fieldName="employee.name"/>
					    <label for="employeeName" class="form-label">Name <span title="Required field" class="text-danger">*</span></label>
					    <s:textfield id="employeeName" name="employee.name" cssClass="form-control" />
					  </div>
					  <s:submit value="Create" cssClass="btn btn-primary"/>
					</s:form>
				</div>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>