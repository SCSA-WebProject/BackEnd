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
					<td><a href="detail?id=${board.id}">${board.title}</a></td>
					<td>${board.region}</td>
					<td>${board.category}</td>
					<td>${board.price}원</td>
				</tr>
			</c:forEach>
		</table>
		<div class="d-flex justify-content-end">
			<a class="btn btn-outline-primary" href="/writeform">맛집 등록</a>
			<a class="btn btn-outline-success" href="/diary">맛집 살펴보기</a>
		</div>
		<c:if test="${pr.prev}">
			<a href="list?page=1">[첫페이지]</a>
			<a href="list?page=${pr.beginPage-1}">[이전]</a>
		</c:if>
		<c:forEach var="i" begin="${pr.beginPage}" end="${pr.endPage}">
			<c:choose>
				<c:when test="${i == pr.page}">
					<strong>[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a href="list?page=${i}">[${i}]</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pr.next}">
			<a href="list?page=${pr.endPage+1}">[다음]</a>
			<a href="list?page=${pr.lastPage}">[마지막페이지]</a>
		</c:if>
	</div>
	<script>
		function movePage(listSize) {
			location.href = "list?listSize=" + listSize;
		}
	</script>
</body>
</html>