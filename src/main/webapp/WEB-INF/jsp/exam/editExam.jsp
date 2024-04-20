<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | Exam Detail</title>
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
						<s:if test="exam != null && exam.id != null">
							<span class="badge bg-light text-dark">
								<s:property value="exam.id" />
							</span>
							<s:form action="deleteExam" class="d-inline">
								<s:hidden name="id" value="%{exam.id}"/>
						    	<button type="submit" class="btn btn-danger float-none float-sm-end" 
						    					onclick="return confirm('Are you sure you want to delete this exam?');">
						    		<i class="bi bi-trash3"></i>
						    	</button>
				    		</s:form>
						</s:if>
					</h2>
				</div>
				<s:if test="exam != null && exam.id != null">
					<div class="card-body">
						<s:form action="updateExam">
							<s:hidden name="exam.id" value="%{exam.id}" />
						  <div class="mb-3">
						  	<s:fielderror fieldName="exam.name"/>
						    <label for="examName" class="form-label">Name <span title="Required field" class="text-danger">*</span></label>
						    <s:textfield name="exam.name" cssClass="form-control" id="examName" />
						  </div>
						  <div class="mb-3 form-check">
						    <s:checkbox name="exam.active" cssClass="form-check-input" id="examActive" label="Active" labelPosition="right" />
						    <label class="form-check-label" for="examActive">Active</label>
						  </div>
						  <div class="mb-3">
						    <label for="examDetail1" class="form-label">Detail 1</label>
						    <s:textarea name="exam.detail1" cssClass="form-control" rows="5" cols="20" id="examDetail1"/>
						  </div>
						  <div class="mb-3">
						    <label for="examDetail2" class="form-label">Detail 2</label>
						    <s:textarea name="exam.detail2" cssClass="form-control" rows="5" cols="20" id="examDetail2"/>
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