<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집 목록</title>
<%@ include file="../common/bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h2>맛집 목록</h2>
		<%@ include file="../common/header.jsp"%>
		<hr>
		<%@include file="../common/searchForm.jsp"%>
		<select id="listSize" onchange="movePage(this.value)">
			<option value="10" <c:if test="${param.listSize==10 }">selected</c:if>>10</option>
			<option value="5" <c:if test="${param.listSize==5 }">selected</c:if>>5</option>
			<option value="20" <c:if test="${param.listSize==20 }">selected</c:if>>20</option>
			<option value="50" <c:if test="${param.listSize==50 }">selected</c:if>>50</option>
		</select>
		<table class="table text-container">
			<tr>
				<th>번호</th>
				<th>이미지</th>
				<th>식당 이름</th>
				<th>지역</th>
				<th>카테고리</th>
				<th>가격대</th>
				<th>좋아요</th>
			</tr>
			<c:forEach items="${boards}" var="board">
				<tr>
					<td>${board.id}</td>
					<td>
						<c:if test="${not empty board.boardFile}">
							<img src="/img${board.boardFile.filePath}/${board.boardFile.systemName}" 
								class="img-thumbnail" 
								style="width: 100px; height: 100px; object-fit: cover;"
								alt="${board.boardFile.oriName}"
								onerror="this.onerror=null; this.src='/img/default.jpg';">
							<!-- 디버깅용 정보 -->
							<c:if test="${empty board.boardFile.filePath}">
								<small class="text-danger">이미지 경로 없음</small>
							</c:if>
						</c:if>
					</td>
					<td><a href="/board/detail?id=${board.id}">${board.title}</a></td>
					<td>${board.region}</td>
					<td>${board.category}</td>
					<td>${board.price}원</td>
					<td>
						<button type="button" class="btn btn-sm ${board.liked ? 'btn-danger' : 'btn-outline-danger'}" 
								onclick="toggleLike(${board.id}, this)" id="likeBtn${board.id}">
							<i class="bi bi-heart${board.liked ? '-fill' : ''}" id="heartIcon${board.id}"></i>
							<span class="like-count" id="likeCount${board.id}">${board.likeCount}</span>
						</button>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="d-flex justify-content-end">
			<a class="btn btn-outline-primary" href="/board/writeform">맛집 등록</a>
		</div>
		<c:if test="${pr.prev}">
			<a href="/board/list?page=1">[첫페이지]</a>
			<a href="/board/list?page=${pr.beginPage-1}">[이전]</a>
		</c:if>
		<c:forEach var="i" begin="${pr.beginPage}" end="${pr.endPage}">
			<c:choose>
				<c:when test="${i == pr.page}">
					<strong>[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a href="/board/list?page=${i}">[${i}]</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pr.next}">
			<a href="/board/list?page=${pr.endPage+1}">[다음]</a>
			<a href="/board/list?page=${pr.lastPage}">[마지막페이지]</a>
		</c:if>
	</div>
	<script>
		function movePage(listSize) {
			location.href = "/board/list?listSize=" + listSize;
		}

		function toggleLike(boardId, button) {
			fetch('/board/likeAjax', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded',
				},
				body: 'boardId=' + boardId
			})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					const heartIcon = document.getElementById('heartIcon' + boardId);
					const likeCount = document.getElementById('likeCount' + boardId);
					
					if (data.liked) {
						button.classList.remove('btn-outline-danger');
						button.classList.add('btn-danger');
						heartIcon.classList.remove('bi-heart');
						heartIcon.classList.add('bi-heart-fill');
					} else {
						button.classList.remove('btn-danger');
						button.classList.add('btn-outline-danger');
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