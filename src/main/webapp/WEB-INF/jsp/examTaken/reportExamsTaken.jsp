<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | Report Exams Taken</title>
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
						<i class="bi bi-clipboard2"></i>
						Exams Taken 
						<span class="badge bg-light text-dark">Report</span>
					</h2>
				</div>
				<div class="card-body">
					<s:if test="hasFieldErrors()">
							<div class="alert alert-danger" role="alert">
						  <s:fielderror />
						</div>
					</s:if>
					<s:form action="generateReportExamsTaken">
					  <div class="row g-3">
						  <div class="col-sm-4">
						  	<label for="examTakenStartDate" class="form-label"> Start Date <span title="Required field" class="text-danger">*</span></label>
						    <s:textfield type="date" id="examTakenStartDate" name="startDate" cssClass="form-control" />
						  </div>
						  <div class="col-sm-4">
						  	<label for="examTakenEndDate" class="form-label"> End Date <span title="Required field" class="text-danger">*</span></label>
						    <s:textfield type="date" id="examTakenEndDate" name="endDate" cssClass="form-control" />
						  </div>
						  <div class="col-auto d-flex flex-column justify-content-end">
						  	<button type="submit" class="btn btn-success" id ="downloadButton">
							  	<i class="bi bi-filetype-xls"> Download</i>
							  </button>
						  </div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
	<script type="text/javascript">
		const downloadButton = document.getElementById('downloadButton');
		if(downloadButton !== null) {
			downloadButton.addEventListener('click', function() {
			    document.querySelectorAll('.alert-danger').forEach(function(element) {
			        element.style.display = 'none';
			    });
			});
		}
	</script>
</body>
</html>