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
				<select class="form-control" id="region" name="region" required>
					<option value="">선택하세요</option>
					<option value="서울">서울</option>
					<option value="경기">경기</option>
					<option value="인천">인천</option>
					<option value="부산">부산</option>
					<option value="대구">대구</option>
					<option value="광주">광주</option>
					<option value="대전">대전</option>
					<option value="울산">울산</option>
					<option value="세종">세종</option>
					<option value="강원">강원</option>
					<option value="충북">충북</option>
					<option value="충남">충남</option>
					<option value="전북">전북</option>
					<option value="전남">전남</option>
					<option value="경북">경북</option>
					<option value="경남">경남</option>
					<option value="제주">제주</option>
				</select>
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
				<label for="content" class="form-label">한줄평(추천메뉴와 후기를 적어주세요)</label>
				<textarea class="form-control" id="content" name="content" rows="5" required></textarea>
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