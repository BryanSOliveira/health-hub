<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | Employee Detail</title>
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
						<i class="bi bi-clipboard2"></i>
						Employee
						<s:if test="employee != null && employee.id != null">
							<span class="badge bg-light text-dark">
								<s:property value="employee.id" />
							</span>
							<s:form action="deleteEmployee" class="d-inline">
								<s:hidden name="id" value="%{employee.id}"/>
						    	<button type="submit" class="btn btn-danger float-none float-sm-end" 
						    					onclick="return confirm('Are you sure you want to delete this employee?');">
						    		<i class="bi bi-trash3"></i>
						    	</button>
				    		</s:form>
						</s:if>
					</h2>
				</div>
				<s:if test="employee != null && employee.id != null">
					<div class="card-body">
						<s:form action="updateEmployee">
							<s:hidden name="employee.id" value="%{employee.id}" />
						  <div class="mb-3">
						  	<s:fielderror fieldName="employee.name"/>
						    <label for="employeeName" class="form-label">Name <span title="Required field" class="text-danger">*</span></label>
						    <s:textfield name="employee.name" cssClass="form-control" id="employeeName" />
						  </div>
						  <button type="submit" class="btn btn-primary">Save</button>
						</s:form>
					</div>
				</s:if>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>