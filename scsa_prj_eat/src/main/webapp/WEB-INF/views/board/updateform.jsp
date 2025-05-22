<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집 정보 수정</title>
<%@ include file="../common/bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h2>맛집 정보 수정</h2>
		<%@ include file="../common/header.jsp"%>
		<form action="update" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${board.id}">
			<div class="mb-3">
				<label for="title" class="form-label">식당 이름</label>
				<input type="text" class="form-control" id="title" name="title" value="${board.title}" required>
			</div>
			<div class="mb-3">
				<label for="region" class="form-label">지역</label>
				<input type="text" class="form-control" id="region" name="region" value="${board.region}" required>
			</div>
			<div class="mb-3">
				<label for="category" class="form-label">카테고리</label>
				<select class="form-control" id="category" name="category" required>
					<option value="">선택하세요</option>
					<option value="한식" <c:if test="${board.category == '한식'}">selected</c:if>>한식</option>
					<option value="양식" <c:if test="${board.category == '양식'}">selected</c:if>>양식</option>
					<option value="중식" <c:if test="${board.category == '중식'}">selected</c:if>>중식</option>
					<option value="일식" <c:if test="${board.category == '일식'}">selected</c:if>>일식</option>
					<option value="아시안" <c:if test="${board.category == '아시안'}">selected</c:if>>아시안</option>
					<option value="술집" <c:if test="${board.category == '술집'}">selected</c:if>>술집</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="price" class="form-label">가격대</label>
				<input type="number" class="form-control" id="price" name="price" value="${board.price}" required>
			</div>
			<div class="mb-3">
				<label for="attach" class="form-label">이미지</label>
				<c:if test="${not empty board.boardFile}">
					<div class="mb-2">
						<img src="/img${board.boardFile.filePath}/${board.boardFile.systemName}" 
							class="img-thumbnail" 
							style="max-width: 200px; max-height: 200px;"
							alt="${board.boardFile.oriName}">
						<p class="mt-1">
							<a href="/download?oriName=${board.boardFile.oriName}&systemName=${board.boardFile.systemName}&filePath=${board.boardFile.filePath}" 
								class="btn btn-sm btn-outline-secondary">
								현재 이미지 다운로드
							</a>
						</p>
					</div>
				</c:if>
				<input type="file" class="form-control" id="attach" name="attach" accept="image/*">
				<small class="form-text text-muted">새 이미지를 선택하면 기존 이미지가 대체됩니다.</small>
			</div>
			<button type="submit" class="btn btn-primary">수정</button>
		</form>
	</div>
</body>
</html>