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
        transition: transform 0.2s;
    }
    .restaurant-card:hover {
        transform: translateY(-5px);
    }
    .restaurant-image {
        width: 100%;
        height: 200px;
        object-fit: cover;
    }
    .card-title {
        font-size: 1.1rem;
        font-weight: bold;
        margin-bottom: 0.5rem;
    }
    .card-text {
        font-size: 0.9rem;
    }
</style>
</head>
<body>
    <div class="container">
        <h2 class="text-center my-4">슥슐랭가이드</h2>
        <%@ include file="common/header.jsp"%>
        <hr>
        
        <div class="d-flex justify-content-end mb-4">
            <a href="/board/writeform" class="btn btn-primary me-2">맛집 등록하기</a>
            <a href="/board/list" class="btn btn-success">맛집 살펴보기</a>
        </div>
        
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
                                    <!-- 디버깅용 정보 -->
                                    <c:if test="${empty board.boardFile.filePath}">
                                        <small class="text-danger">이미지 경로 없음</small>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty board.boardFile}">
                                    <img src="/img/default.jpg" 
                                        class="card-img-top restaurant-image" 
                                        alt="기본 이미지">
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
                                    <!-- 디버깅용 정보 -->
                                    <c:if test="${empty board.boardFile.filePath}">
                                        <small class="text-danger">이미지 경로 없음</small>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty board.boardFile}">
                                    <img src="/img/default.jpg" 
                                        class="card-img-top restaurant-image" 
                                        alt="기본 이미지">
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