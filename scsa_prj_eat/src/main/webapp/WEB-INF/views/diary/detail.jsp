<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일기 상세보기</title>
    <%@ include file="../common/bootstrap.jsp" %>
</head>
<body>
    <div class="container mt-5">
        <h2>📖 일기 상세</h2>
        <%@ include file="../common/header.jsp" %>
        <hr>

        <div class="mb-3">
            <h4>${diary.title}</h4>
            <p><strong>날짜:</strong> ${diary.date}</p>
            <p>${diary.context}</p>
        </div>

        <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/diary/list" class="btn btn-secondary">목록으로</a>
            <div>
                <a href="${pageContext.request.contextPath}/diary/edit/${diary.id}" class="btn btn-primary">수정</a>
                <a href="${pageContext.request.contextPath}/diary/delete/${diary.id}" class="btn btn-danger"
                   onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
            </div>
        </div>
    </div>
</body>
</html>
