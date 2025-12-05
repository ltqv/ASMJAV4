<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
body {
    background-color: #ffe6f0; /* hồng nhạt */
}

.card {
    border: 2px solid #ff66b3; /* viền hồng */
}

.card-header {
    background-color: #ff66b3; /* hồng đậm */
    color: white;
}

.btn-primary {
    background-color: #ff66b3;
    border-color: #ff66b3;
}

.btn-primary:hover {
    background-color: #ff3399;
    border-color: #ff3399;
}

.alert-danger {
    background-color: #ffb3c6;
    color: #800000;
    border: 1px solid #ff66b3;
}
</style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header text-center">
                    <h4>ĐĂNG NHẬP</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger">${message}</div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="id" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                    </form>
                </div>
                <div class="card-footer text-center">
                    Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
                    <br>
                    <a href="${pageContext.request.contextPath}/home">Quay về trang chủ</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
