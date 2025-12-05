<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
body {
    background-color: #ffe6f0; /* hồng nhạt */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.card {
    border: 2px solid #ff66b3; /* viền hồng */
    background-color: #fff0f5; /* hồng nhạt */
}

.card-header {
    background-color: #ff66b3; /* hồng đậm */
    color: white;
    font-weight: bold;
}

.btn-primary, .btn-success {
    background-color: #ff66b3;
    border-color: #ff66b3;
    color: white;
    font-weight: bold;
}

.btn-primary:hover, .btn-success:hover {
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
                    <h4>ĐĂNG KÝ THÀNH VIÊN</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger">${message}</div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="mb-3">
                            <label>Tên đăng nhập (ID)</label>
                            <input type="text" name="id" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Họ và tên</label>
                            <input type="text" name="fullname" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Đăng ký</button>
                    </form>
                </div>
                <div class="card-footer text-center">
                    Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
