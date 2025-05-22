<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일기 작성</title>
    <%@ include file="../common/bootstrap.jsp" %>
</head>
<body>
    <div class="container mt-5">
        <h2>📔 일기 작성</h2>
        <%@ include file="../common/header.jsp" %>
        <hr>

        <form action="${pageContext.request.contextPath}/diary/write" method="post">
            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>

            <div class="mb-3">
                <label for="date" class="form-label">날짜</label>
                <input type="date" class="form-control" id="date" name="date">
            </div>

            <div class="mb-3">
                <label for="context" class="form-label">내용</label>
                <textarea class="form-control" id="context" name="context" rows="5" required></textarea>
            </div>

            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/diary/list" class="btn btn-secondary">취소</a>
                <button type="submit" class="btn btn-primary">작성 완료</button>
            </div>
        </form>
    </div>
</body>
</html>
