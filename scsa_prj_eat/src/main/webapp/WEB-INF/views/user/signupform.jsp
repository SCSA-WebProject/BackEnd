<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<%@ include file="../common/bootstrap.jsp" %>
</head>
<body>
	<div class="container">
		<h2>회원가입</h2>
		<form action="signup" method="POST">
			<div class="mb-3">
				<label for="id" class="form-label">ID</label>
				<input type="text" class="form-control" id="id" name="id">
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">PW</label>
				<input type="password" class="form-control" id="password" name="password">
			</div>
			<div class="mb-3">
				<label for="name" class="form-label">이름</label>
				<input type="text" class="form-control" id="name" name="name">
			</div>
			<div class="mb-3">
				<label for="phone" class="form-label">전화번호</label>
				<input type="tel" class="form-control" id="phone" name="phone" placeholder="010-0000-0000">
			</div>
			<div class="mb-3">
				<label for="company" class="form-label">회사</label>
				<select name="companyCode" class="form-select" id="company">
					<option value="100">dx</option>
					<option value="200">ds</option>
					<option value="300">sds</option>
				</select>
			</div>
			<button class="btn btn-primary">회원가입</button>
		</form>
	</div>
</body>
</html>