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
					<h5 class="card-title">
						<i class="bi bi-clipboard2"></i>
						Exam 
						<span class="badge bg-light text-dark">1</span>
						<a class="btn btn-secondary btn-sm" href="exam">
							<i class="bi bi-arrow-90deg-left"> Back</i>
						</a>
					</h5>
				</div>
				<div class="card-body">
					<form>
					  <div class="mb-3">
					    <label for="examName" class="form-label">Name</label>
					    <input type="text" class="form-control" id="examName">
					  </div>
					  <div class="mb-3 form-check">
					    <input type="checkbox" class="form-check-input" id="examActive">
					    <label class="form-check-label" for="examActive">Active</label>
					  </div>
					  <div class="mb-3">
					    <label for="examDetail1" class="form-label">Detail 1</label>
					    <textarea class="form-control" rows="5" cols="20" id="examDetail1"></textarea>
					  </div>
					  <div class="mb-3">
					    <label for="examDetail2" class="form-label">Detail 2</label>
					    <textarea class="form-control" rows="5" cols="20" id="examDetail2"></textarea>
					  </div>
					  <button type="submit" class="btn btn-primary">Save</button>
					</form>
				</div>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>