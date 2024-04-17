<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Health Hub | Exam</title>
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
					</h5>
				</div>
				<div class="card-body">
					<!-- Toolbar -->
					<div class="btn-toolbar" role="toolbar">
						<div class="input-group mb-3 me-2">
							<span class="input-group-text">
								<i class="bi bi-list-ol"></i>
							</span>
							<select class="form-select">
							  <option value="1" selected>25</option>
							  <option value="2">50</option>
							  <option value="3">75</option>
							  <option value="4">100</option>
							</select>
						</div>
						<nav class="me-2">
						  <ul class="pagination">
						    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
						    <li class="page-item"><a class="page-link active" href="#">1</a></li>
						    <li class="page-item"><a class="page-link" href="#">Next</a></li>
						  </ul>
						</nav>
						<div class="input-group mb-3">
							<select class="form-select">
							  <option value="1">ID</option>
							  <option value="2" selected>Name</option>
							</select>
						  <input type="text" class="form-control">
						  <button class="btn btn-secondary">
						  	Search
						  </button>
						</div>
						<div class="ms-2">
							<button class="btn btn-primary">
								ADD NEW
							</button>
						</div>
						<div class="ms-auto">
							<button class="btn btn-secondary">
								Export
							</button>
						</div>
					</div>
					<!--  Table  -->
					<div class="table-responsive">
						<table class="table table-striped table-hover table-bordered">
						  <thead class="table-light">
						    <tr>
						      <th>ID</th>
						      <th>Name</th>
						      <th>Active</th>
						      <th>Detail 1</th>
						      <th>Detail 2</th>
						      <th style="width: 5%;"></th>
						    </tr>
						  </thead>
						  <tbody>
						    <tr>
						      <th>
						      	<a href="exam/1">
						      		1
						      	</a>
						      </th>
						      <td>Mark</td>
						      <td>Otto</td>
						      <td>@mdo</td>
						      <td>@mdo</td>
						      <td>
						      	<button class="btn btn-danger btn-sm">
						      		Excluir
						      	</button>
						      </td>
						    </tr>
						  </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>