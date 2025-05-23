<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<div>
	<c:if test="${empty loginUser}">
		<a href="${pageContext.request.contextPath}/login" class="btn btn-outline-warning">로그인</a>
		<a href="${pageContext.request.contextPath}/signup" class="btn btn-outline-primary">회원가입</a>
	</c:if>
	<c:if test="${not empty loginUser}">
		${loginUser}님 반갑습니다.
		<a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger">로그아웃</a>
		<c:if test="${'admin' eq loginUser }">
			<a href="users">관리자페이지</a>
		</c:if>
	</c:if>
</div>
<hr>