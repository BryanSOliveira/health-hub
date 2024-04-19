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
					<h2 class="card-title">
						<i class="bi bi-clipboard2"></i>
						Exams
					</h2>
				</div>
				<div class="card-body">
					<!-- Toolbar -->
					<div class="btn-toolbar" role="toolbar">
						<s:form action="exams">
							<s:hidden name="criteria.activeFilter" value="%{criteria.activeFilter}" />
							<s:hidden name="criteria.searchQuery" value="%{criteria.searchQuery}" />
							
							<div class="input-group mb-3 me-2">
								<span class="input-group-text">
									<i class="bi bi-list-ol"></i>
								</span>
								
								<s:select name="criteria.pageSize"
								          class="form-select"
								          list="#{'25':'25','50':'50','75':'75','100':'100'}"
								          value="%{criteria.pageSize}"
								          onchange="this.form.submit()"/>
							
							</div>
						</s:form>
						
						<!-- Pagination -->
						<nav class="me-2">
						  <ul class="pagination">
						  	<s:form action="exams" cssClass="page-item">
									<s:hidden name="criteria.currentPage" value="%{criteria.currentPage - 1}"/>
							    <s:hidden name="criteria.pageSize" value="%{criteria.pageSize}"/>
							    <s:hidden name="criteria.activeFilter" value="%{criteria.activeFilter}"/>
							    <s:hidden name="criteria.searchQuery" value="%{criteria.searchQuery}"/>
							    <button type="submit" class="page-link" <s:if test="criteria.currentPage == 1">disabled</s:if>>
							    	<span aria-hidden="true">&laquo;</span>
							    </button>
								</s:form>
								<li class="page-item">
								    <span class="page-link active"><s:property value="criteria.currentPage"/></span>
								</li>
								<s:form action="exams" cssClass="page-item">
								    <s:hidden name="criteria.currentPage" value="%{criteria.currentPage + 1}"/>
								    <s:hidden name="criteria.pageSize" value="%{criteria.pageSize}"/>
								    <s:hidden name="criteria.activeFilter" value="%{criteria.activeFilter}"/>
								    <s:hidden name="criteria.searchQuery" value="%{criteria.searchQuery}"/>
								    <button type="submit" class="page-link" <s:if test="exams.size() < criteria.pageSize">disabled</s:if>>
								    	<span aria-hidden="true">&raquo;</span>
								    </button>
								</s:form>
						  </ul>
						</nav>
						
						<!-- Active filter -->
						<s:form action="exams">
							<s:hidden name="criteria.pageSize" value="%{criteria.pageSize}" />
							<s:hidden name="criteria.activeFilter" value="%{criteria.activeFilter}" />
							<div class="input-group mb-3">
							 	<s:textfield cssClass="form-control" name="criteria.searchQuery" placeholder="Search by ID or Name"/>
							  <button type="submit" class="btn btn-secondary">
							  	<i class="bi bi-search"></i>
							  </button>
							</div>
						</s:form>
						<div class="ms-2">
							<a href="newExam" class="btn btn-primary">
								<i class="bi bi-plus-lg"></i> ADD
							</a>
						</div>
					</div>
					<!-- Filter active -->
					<s:form action="exams" id="activeFilterForm">
				    <s:hidden name="criteria.pageSize" value="%{criteria.pageSize}" />
				    <s:hidden name="criteria.searchQuery" value="%{criteria.searchQuery}" />
				    <div class="mb-2">
				        <s:radio name="criteria.activeFilter"
				                 list="#{'active':'Active Exams', 'inactive':'Inactive Exams', 'all':'Both'}"
				                 value="%{criteria.activeFilter}"
				                 onchange="this.form.submit()"
				                 cssClass="me-1"
				                 labelPosition="right"/>
				    </div>
					</s:form>
					
					<!-- Total Records -->
					<small><b><s:property value="exams.size()" /> records</b></small>
					
					<!--  Table  -->
					<div class="table-responsive">
						<table class="table table-striped table-hover table-bordered">
						  <thead class="table-light">
						    <tr>
						      <th>ID</th>
						      <th>Name</th>
						      <th>Active</th>
						      <th style="width: 25%;">Detail 1</th>
						      <th style="width: 25%;">Detail 2</th>
						      <th style="width: 5%;"></th>
						    </tr>
						  </thead>
						  <tbody>
						  	<s:iterator value="exams">
							    <tr>
							      <th>
							      	<a href="<s:url action='editExam'><s:param name='id' value='%{id}'/></s:url>">
							      		<s:property value="id" />
							      	</a>
							      </th>
							      <td><s:property value="name" /></td>
							      <td class="text-center">
								      <s:if test="active">
								      	✅
								      </s:if>
								      <s:else>
								      	❌
								      </s:else>
							      </td>
							      <td class="text-break"><s:property value="detail1" /></td>
							      <td class="text-break"><s:property value="detail2" /></td>
							      <td>
							      	<s:form action="deleteExam" class="d-inline">
												<s:hidden name="id" value="%{id}"/>
												<s:hidden name="criteria.currentPage" value="%{criteria.currentPage}"/>
										    <s:hidden name="criteria.pageSize" value="%{criteria.pageSize}"/>
										    <s:hidden name="criteria.activeFilter" value="%{criteria.activeFilter}"/>
										    <s:hidden name="criteria.searchQuery" value="%{criteria.searchQuery}"/>
									    	<button type="submit" class="btn btn-danger float-none float-sm-end" 
									    					onclick="return confirm('Are you sure you want to delete this exam?');">
									    		<i class="bi bi-trash3"></i>
									    	</button>
								    	</s:form>
							      </td>
							    </tr>
						    </s:iterator>
						  </tbody>
						</table>
					</div>
					
					<!-- Total Records -->
					<small><b><s:property value="exams.size()" /> records</b></small>
					
				</div>
			</div>
		</div>
	</s:if>
	<%@ include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>