<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일기 목록</title>
<%@ include file="../common/bootstrap.jsp" %>
</head>
<body>
	<div class="container">
		<h2>📘 일기 목록</h2>
		<%@ include file="../common/header.jsp" %>
		<hr>
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="diary">
					<tr>
						<td>${diary.id}</td>
						<td><a href="detail/${diary.id}">${diary.title}</a></td>
						<td>${diary.date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="d-flex justify-content-end gap-2 mt-3">
			<a class="btn btn-outline-primary" href="write">✏️ 새 일기 작성</a>
			<a class="btn btn-outline-secondary" href="/board/list">맛집 목록으로</a>
		</div>
	</div>
</body>
</html>
