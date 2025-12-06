<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>LTQV</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
body {
    background-color: #ffe6f0; /* hồng nhạt */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Sidebar màu hồng */
.sidebar {
    height: 100vh;
    width: 220px;
    background-color: #ff66b3;
    color: white;
    padding-top: 20px;
    position: fixed;
}

.sidebar a {
    display: block;
    color: white;
    text-decoration: none;
    padding: 10px 20px;
    font-weight: bold;
}

.sidebar a:hover {
    background-color: #ff3399;
    color: white;
}

/* Header */
.header {
    margin-left: 220px;
    padding: 20px;
    text-align: center;
    background-color: #ffb3c6;
    color: #800000;
    border-radius: 0 0 10px 10px;
}

/* Nội dung chính */
.content {
    margin-left: 220px;
    padding: 20px;
}

.content-card {
    background-color: #fff0f5;
    border: 2px solid #ff66b3;
    border-radius: 10px;
    padding: 20px;
    min-height: 500px;
}

/* Footer */
footer {
    margin-left: 220px;
    background-color: #ffb3c6;
    color: #800000;
    padding: 20px;
    text-align: center;
    font-weight: bold;
}

/* Navbar user (sidebar) */
.user-section {
    margin-top: 20px;
    border-top: 1px solid #ff99cc;
    padding-top: 10px;
}
</style>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <h4 class="text-center mb-4">Lofi chill LTQV</h4>
    <a href="${pageContext.request.contextPath}/home"><i class="bi bi-house me-2"></i>Trang Chủ</a>
<a href="${pageContext.request.contextPath}/favorites"><i class="bi bi-heart me-2"></i>Yêu thích</a>
<a href="${pageContext.request.contextPath}/trending"><i class="bi bi-bar-chart me-2"></i>Thịnh hành</a>
    <div class="user-section">
        <c:choose>
            <c:when test="${empty sessionScope.currentUser}">
                <a href="${pageContext.request.contextPath}/login" class="mt-2"><i class="bi bi-box-arrow-in-right me-2"></i>Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register"><i class="bi bi-person-plus me-2"></i>Đăng ký</a>
            </c:when>
            <c:otherwise>
                <span class="d-block mb-2 fw-bold ps-3">${sessionScope.currentUser.fullname}</span>
                <a href="${pageContext.request.contextPath}/editProfile"><i class="bi bi-person-gear me-2"></i>Hồ sơ</a>
                <a href="${pageContext.request.contextPath}/changePassword"><i class="bi bi-key me-2"></i>Đổi mật khẩu</a>
                <c:if test="${sessionScope.currentUser.admin}">
                    <a href="${pageContext.request.contextPath}/admin/videoManager"><i class="bi bi-shield-lock me-2"></i>Quản trị</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Đăng xuất</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Header -->
<div class="header">
    <h1>Khám Phá Thế Giới Giải Trí</h1>
    <p>Xem video yêu thích, chia sẻ khoảnh khắc của bạn</p>
</div>

<!-- Nội dung chính -->
<div class="content">
    <div class="content-card">
        <jsp:include page="${page}"></jsp:include>
    </div>
</div>

<!-- Footer -->
<footer>
    &copy; Ltqv page of the Year
</footer>

</body>
</html>
