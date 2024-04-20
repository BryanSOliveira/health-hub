<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | New Exam Taken</title>
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
						<a class="btn btn-secondary" href="examsTaken">
							<i class="bi bi-arrow-90deg-left"> Back</i>
						</a>		
						<i class="bi bi-people"></i>
						Exam Taken 
						<span class="badge bg-light text-dark">NEW</span>
					</h2>
				</div>
				<div class="card-body">
					<s:if test="hasFieldErrors()">
						<div class="alert alert-danger" role="alert">
						  <s:fielderror />
						</div>
					</s:if>
					<s:form action="createExamTaken">
						<div class="row g-3">
						  <div class="col-sm-4">
						    <label for="examTakenEmployeeId" class="form-label">Employee <span title="Required field" class="text-danger">*</span></label>
						  	<s:select list="employees" 
						  						listKey="id" 
						  						listValue="name" 
						  						name="examTaken.employee.id" 
						  						cssClass="form-select"
						  						headerKey=""
	          							headerValue="Chose..."
	          							id="examTakenEmployeeId"/>
						  </div>
						  <div class="col-sm-4">
						    <label for="examTakenExamId" class="form-label">Exam <span title="Required field" class="text-danger">*</span></label>
						  	<s:select list="exams" 
						  						listKey="id" 
						  						listValue="name" 
						  						name="examTaken.exam.id" 
						  						cssClass="form-select"
						  						headerKey=""
	          							headerValue="Chose..."
	          							id="examTakenExamId"/>
						  </div>
						  <div class="col-sm-4">
						    <label for="examTakenDate" class="form-label">Date <span title="Required field" class="text-danger">*</span></label>
						    <s:textfield type="date" id="examTakenDate" name="examTaken.date" cssClass="form-control" />
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