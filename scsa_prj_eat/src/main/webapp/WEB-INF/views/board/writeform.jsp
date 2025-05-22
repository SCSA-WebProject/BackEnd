<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집 등록</title>
<%@ include file="../common/bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h2>맛집 등록</h2>
		<%@ include file="../common/header.jsp"%>
		<form action="write" method="POST" enctype="multipart/form-data">
			<div class="mb-3">
				<label for="title" class="form-label">식당 이름</label>
				<input type="text" class="form-control" id="title" name="title" required>
			</div>
			<div class="mb-3">
				<label for="region" class="form-label">지역</label>
				<input type="text" class="form-control" id="region" name="region" required>
			</div>
			<div class="mb-3">
				<label for="category" class="form-label">카테고리</label>
				<select class="form-control" id="category" name="category" required>
					<option value="">선택하세요</option>
					<option value="한식">한식</option>
					<option value="양식">양식</option>
					<option value="중식">중식</option>
					<option value="일식">일식</option>
					<option value="아시안">아시안</option>
					<option value="술집">술집</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="price" class="form-label">가격대</label>
				<input type="number" class="form-control" id="price" name="price" required>
			</div>
			<div class="mb-3">
				<label for="attach" class="form-label">이미지 첨부</label>
				<input type="file" class="form-control" id="attach" name="attach" accept="image/*">
			</div>
			<button type="submit" class="btn btn-primary">등록</button>
		</form>
	</div>
</body>
</html>