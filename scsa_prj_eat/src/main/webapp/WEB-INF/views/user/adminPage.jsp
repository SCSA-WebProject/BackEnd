<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<%@ include file="../common/bootstrap.jsp" %>
</head>
<body>
	<div class="container">
		<%@ include file="../common/header.jsp" %>
		<h2>사용자 목록(관리자만 접근 가능)</h2>
		<hr>
		<div>
			<table class="table text-center">
				<tr>
					<th>ID</th>
					<th>PW</th>
					<th>NAME</th>
					<th>PHONE</th>
					<th>COMPANY</th>
				</tr>
				<c:forEach items="${userList}" var="user">
					<tr>
						<td>${user.id}</td>
						<td>${user.password}</td>
						<td>${user.name}</td>
						<td>${user.phone}</td>
						<td>${user.companyName}</td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="d-flex justify-content-end">
				<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/list">홈으로</a>
			</div>
		</div>
	</div>
</body>
</html>