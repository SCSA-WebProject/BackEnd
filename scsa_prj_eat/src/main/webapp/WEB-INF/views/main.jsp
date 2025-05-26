<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>슥슐랭가이드 메인</title>
<%@ include file="common/bootstrap.jsp"%>
<style>
    .restaurant-card {
        height: 100%;
    }
    .restaurant-image {
        width: 100%;
        height: 200px;
        object-fit: cover;
    }
</style>
</head>
<body>
    <div class="container">
        <h2 class="text-center my-4">슥슐랭가이드</h2>
        <%@ include file="common/header.jsp"%>
        <hr>
        
        <!-- 최근 등록된 맛집 -->
        <div class="row mb-5">
            <div class="col-12">
                <h3 class="mb-4">최근 등록된 맛집</h3>
                <div class="row row-cols-1 row-cols-md-5 g-4">
                    <c:forEach items="${recentBoards}" var="board">
                        <div class="col">
                            <div class="card restaurant-card">
                                <c:if test="${not empty board.boardFile}">
                                    <img src="/img${board.boardFile.filePath}/${board.boardFile.systemName}" 
                                        class="card-img-top restaurant-image" 
                                        alt="${board.boardFile.oriName}"
                                        onerror="this.onerror=null; this.src='/img/default.jpg';">
                                </c:if>
                                <div class="card-body">
                                    <h5 class="card-title">${board.title}</h5>
                                    <p class="card-text">
                                        <small class="text-muted">${board.region} | ${board.category}</small><br>
                                        <small class="text-muted">${board.price}원</small>
                                    </p>
                                    <a href="/board/detail?id=${board.id}" class="btn btn-primary btn-sm">자세히 보기</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <!-- 인기 맛집 -->
        <div class="row">
            <div class="col-12">
                <h3 class="mb-4">인기 맛집</h3>
                <div class="row row-cols-1 row-cols-md-5 g-4">
                    <c:forEach items="${popularBoards}" var="board">
                        <div class="col">
                            <div class="card restaurant-card">
                                <c:if test="${not empty board.boardFile}">
                                    <img src="/img${board.boardFile.filePath}/${board.boardFile.systemName}" 
                                        class="card-img-top restaurant-image" 
                                        alt="${board.boardFile.oriName}"
                                        onerror="this.onerror=null; this.src='/img/default.jpg';">
                                </c:if>
                                <div class="card-body">
                                    <h5 class="card-title">${board.title}</h5>
                                    <p class="card-text">
                                        <small class="text-muted">${board.region} | ${board.category}</small><br>
                                        <small class="text-muted">${board.price}원</small><br>
                                        <small class="text-danger"><i class="bi bi-heart-fill"></i> ${board.likeCount}</small>
                                    </p>
                                    <a href="/board/detail?id=${board.id}" class="btn btn-primary btn-sm">자세히 보기</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 