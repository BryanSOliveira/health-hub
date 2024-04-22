<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | Exam Taken Detail</title>
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
						<s:if test="examTaken != null && examTaken.id != null">
							<span class="badge bg-light text-dark">
								<s:property value="examTaken.id" />
							</span>
							<s:form action="deleteExamTaken" class="d-inline">
								<s:hidden name="id" value="%{examTaken.id}"/>
						    	<button type="submit" class="btn btn-danger float-none float-sm-end" 
						    					onclick="return confirm('Are you sure you want to delete this exam taken?');">
						    		<i class="bi bi-trash3"></i>
						    	</button>
				    		</s:form>
						</s:if>
					</h2>
				</div>
				<s:if test="examTaken != null && examTaken.id != null">
					<div class="card-body">
						<s:if test="hasFieldErrors()">
							<div class="alert alert-danger" role="alert">
							  <s:fielderror />
							</div>
						</s:if>
						<s:form action="updateExamTaken">
							<s:hidden name="examTaken.id" value="%{examTaken.id}" />
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
						  <button type="submit" class="btn btn-primary mt-2">Save</button>
						</s:form>
					</div>
				</s:if>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>