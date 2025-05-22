<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집 상세보기</title>
<%@ include file="../common/bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h2>맛집 상세보기</h2>
		<hr>
		<%@ include file="../common/header.jsp"%>
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">${board.title}</h5>
				<div class="d-flex justify-content-between">
					<div class="card-subtitle">지역: ${board.region}</div>
					<div class="card-subtitle">카테고리: ${board.category}</div>
					<div class="card-subtitle">가격대: ${board.price}원</div>
				</div>
				<c:if test="${not empty board.boardFile}">
					<div class="mt-3">
						<img src="/img${board.boardFile.filePath}/${board.boardFile.systemName}" 
							class="img-fluid rounded" 
							style="max-width: 500px; max-height: 500px;"
							alt="${board.boardFile.oriName}">
						<p class="mt-2">
							<a href="/download?oriName=${board.boardFile.oriName}&systemName=${board.boardFile.systemName}&filePath=${board.boardFile.filePath}" 
								class="btn btn-sm btn-outline-secondary">
								원본 이미지 다운로드
							</a>
						</p>
					</div>
				</c:if>
				<div>
					<a href="delete?id=${board.id}" class="btn btn-info">삭제</a>
					<a href="updateform?id=${board.id}" class="btn btn-success">수정</a>
					<a href="list" class="btn btn-warning">목록</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>