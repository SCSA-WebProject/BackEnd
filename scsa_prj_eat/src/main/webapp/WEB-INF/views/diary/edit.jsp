<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일기 수정</title>
    <%@ include file="../common/bootstrap.jsp" %>
</head>
<body>
    <div class="container mt-5">
        <h2>✏️ 일기 수정</h2>
        <%@ include file="../common/header.jsp" %>
        <hr>

        <form action="${pageContext.request.contextPath}/diary/edit" method="post">
            <!-- 수정 시 필요한 id -->
            <input type="hidden" name="id" value="${diary.id}"/>

            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" id="title" name="title" value="${diary.title}" required>
            </div>

            <div class="mb-3">
                <label for="date" class="form-label">날짜</label>
                <input type="date" class="form-control" id="date" name="date" value="${diary.date}">
            </div>

            <div class="mb-3">
                <label for="context" class="form-label">내용</label>
                <textarea class="form-control" id="context" name="context" rows="5" required>${diary.context}</textarea>
            </div>

            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/diary/detail/${diary.id}" class="btn btn-secondary">취소</a>
                <button type="submit" class="btn btn-primary">수정 완료</button>
            </div>
        </form>
    </div>
</body>
</html>
