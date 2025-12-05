<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Hẹ hẹ</title>

<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
    rel="stylesheet">
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
body {
    min-height: 100vh;
}

.sidebar {
    height: 100vh;
    background-color: #343a40; /* xám đậm */
    color: white;
    padding-top: 20px;
}

.sidebar a {
    display: block;
    color: white;
    padding: 10px 20px;
    text-decoration: none;
    font-weight: bold;
}

.sidebar a:hover {
    background-color: #495057;
    color: #ffc107;
}

.content {
    padding: 20px;
    background-color: #f8f9fa; /* trắng xám */
    min-height: 100vh;
}

.card.admin-card {
    border: 1px solid #ff6600; /* viền cam */
    background-color: #fff3cd; /* vàng nhạt */
}

footer {
    text-align: center;
    padding: 10px;
    background-color: #343a40;
    color: white;
    font-weight: bold;
}
</style>
</head>
<body>

<div class="d-flex">

    <!-- Sidebar -->
    <div class="sidebar flex-shrink-0">
        <h4 class="text-center mb-4">Page Controll</h4>
        <a href="${pageContext.request.contextPath}/admin/videoManager"><i class="bi bi-film me-2"></i>Video</a>
        <a href="${pageContext.request.contextPath}/admin/userManager"><i class="bi bi-people me-2"></i>Người dùng</a>
        <a href="${pageContext.request.contextPath}/admin/report"><i class="bi bi-bar-chart me-2"></i>Báo cáo</a>
        <hr class="bg-light">
        <a href="${pageContext.request.contextPath}/home" target="_blank"><i class="bi bi-house me-2"></i>Trang chủ</a>
        <p class="mt-3 text-center small">Xin chào, ${sessionScope.currentUser.fullname}</p>
    </div>

    <!-- Main content -->
    <div class="content flex-grow-1">
        <div class="card admin-card p-3">
            <div class="card-header">
                <h5>Bảng điều khiển</h5>
            </div>
            <div class="card-body" style="min-height: 500px;">
                <jsp:include page="${page}"></jsp:include>
            </div>
        </div>
    </div>

</div>

<footer>
    &copy; Ltqv Page of the Year
</footer>

</body>
</html>
