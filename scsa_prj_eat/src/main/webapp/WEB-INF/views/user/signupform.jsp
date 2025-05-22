<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<%@ include file="../common/bootstrap.jsp" %>

<script>
let isIdChecked = false;
let isIdAvailable = false;

function checkId() {
    var userId = document.getElementById("id").value;
    if (!userId) {
        alert("아이디를 입력해주세요.");
        return;
    }

    fetch("/checkId?id=" + userId)
        .then(response => response.text())
        .then(result => {
            if (result === "duplicate") {
                alert("이미 사용 중인 아이디입니다.");
                isIdAvailable = false;
                document.getElementById("id").readonly = false;
            } else {
                alert("사용 가능한 아이디입니다.");
                isIdAvailable = true;
                document.getElementById("id").readonly = true;
            }
            isIdChecked = true;
            validateForm();
        });
}

function validateForm() {
    const id = document.getElementById("id").value;
    const password = document.getElementById("password").value;
    const name = document.getElementById("name").value;
    const phone = document.getElementById("phone").value;
    const company = document.getElementById("company").value;
    
    const signupButton = document.getElementById("signupButton");
    
    if (id && password && name && phone && company && isIdChecked && isIdAvailable) {
        signupButton.disabled = false;
    } else {
        signupButton.disabled = true;
    }
}

// 페이지 로드 시 회원가입 버튼 비활성화
window.onload = function() {
    document.getElementById("signupButton").disabled = true;
    
    // 모든 입력 필드에 이벤트 리스너 추가
    const inputs = document.querySelectorAll('input, select');
    inputs.forEach(input => {
        input.addEventListener('input', validateForm);
    });
}
</script>

</head>
<body>
	<div class="container">
		<h2>회원가입</h2>
		<form action="signup" method="POST">
			<div class="mb-3">
				<label for="id" class="form-label">ID</label>
				<div class="input-group">
					<input type="text" class="form-control" id="id" name="id" required>
					<button type="button" class="btn btn-outline-secondary" onclick="checkId()">중복 확인</button>
				</div>
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">PW</label>
				<input type="password" class="form-control" id="password" name="password" required>
			</div>
			<div class="mb-3">
				<label for="name" class="form-label">이름</label>
				<input type="text" class="form-control" id="name" name="name" required>
			</div>
			<div class="mb-3">
				<label for="phone" class="form-label">전화번호</label>
				<input type="tel" class="form-control" id="phone" name="phone" placeholder="010-0000-0000" required>
			</div>
			<div class="mb-3">
				<label for="company" class="form-label">회사</label>
				<select name="companyCode" class="form-select" id="company" required>
					<option value="">회사를 선택하세요</option>
					<option value="100">DX</option>
					<option value="200">DS</option>
					<option value="300">SDS</option>
				</select>
			</div>
			<button type="submit" class="btn btn-primary" id="signupButton">회원가입</button>
		</form>
	</div>
</body>
</html>
