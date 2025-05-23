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
					<div class="card-subtitle">
						<button type="button" class="btn btn-sm ${board.liked ? 'btn-danger' : 'btn-outline-danger'}" 
								onclick="toggleLike(${board.id})" id="likeBtn">
							<i class="bi bi-heart${board.liked ? '-fill' : ''}" id="heartIcon"></i>
							<span class="like-count" id="likeCount">${board.likeCount}</span>
						</button>
					</div>
				</div>
				<div class="card-subtitle mb-3">작성자: ${board.userId}</div>
				<c:if test="${not empty board.boardFile}">
					<div class="mt-3">
						<img src="/img${board.boardFile.filePath}/${board.boardFile.systemName}" 
							class="img-fluid rounded" 
							style="max-width: 500px; max-height: 500px;"
							alt="${board.boardFile.oriName}">
					</div>
				</c:if>
				<div>
					<c:if test="${sessionScope.loginUser eq board.userId}">
						<a href="delete?id=${board.id}" class="btn btn-info">삭제</a>
						<a href="updateform?id=${board.id}" class="btn btn-success">수정</a>
					</c:if>
					<a href="list" class="btn btn-warning">목록</a>
				</div>
			</div>
		</div>
	</div>
	<script>
		function toggleLike(boardId) {
			fetch('likeAjax', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded',
				},
				body: 'boardId=' + boardId
			})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					const likeBtn = document.getElementById('likeBtn');
					const heartIcon = document.getElementById('heartIcon');
					const likeCount = document.getElementById('likeCount');
					
					if (data.liked) {
						likeBtn.classList.remove('btn-outline-danger');
						likeBtn.classList.add('btn-danger');
						heartIcon.classList.remove('bi-heart');
						heartIcon.classList.add('bi-heart-fill');
					} else {
						likeBtn.classList.remove('btn-danger');
						likeBtn.classList.add('btn-outline-danger');
						heartIcon.classList.remove('bi-heart-fill');
						heartIcon.classList.add('bi-heart');
					}
					
					likeCount.textContent = data.likeCount;
				} else {
					alert(data.message);
				}
			})
			.catch(error => {
				console.error('Error:', error);
				alert('좋아요 처리 중 오류가 발생했습니다.');
			});
		}
	</script>
</body>
</html>