<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | New Exam</title>
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
						<a class="btn btn-secondary" href="exams">
							<i class="bi bi-arrow-90deg-left"> Back</i>
						</a>
						<i class="bi bi-clipboard2"></i>
						Exam 
						<span class="badge bg-light text-dark">NEW</span>
					</h2>
				</div>
				<div class="card-body">
					<s:form action="createExam">
					  <div class="mb-3">
					  	<s:fielderror fieldName="exam.name"/>
					    <label for="examName" class="form-label">Name <span title="Required field" class="text-danger">*</span></label>
					    <s:textfield id="examName" name="exam.name" cssClass="form-control" />
					  </div>
					  <div class="mb-3 form-check">
					  	<s:fielderror fieldName="exam.active"/>
					    <s:checkbox id="examActive" name="exam.active" cssClass="form-check-input" labelPosition="right"/>
					    <label class="form-check-label" for="examActive">Active</label>
					  </div>
					  <div class="mb-3">
					  	<s:fielderror fieldName="exam.detail1"/>
					    <label for="examDetail1" class="form-label">Detail 1</label>
					    <s:textarea id="examDetail1" name="exam.detail1" cssClass="form-control" rows="5" cols="20"/>
					  </div>
					  <div class="mb-3">
					  	<s:fielderror fieldName="exam.detail2"/>
					    <label for="examDetail2" class="form-label">Detail 2</label>
					    <s:textarea id="examDetail2" name="exam.detail2" cssClass="form-control" rows="5" cols="20"/>
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