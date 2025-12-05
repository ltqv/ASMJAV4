<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đổi Mật Khẩu</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center min-vh-100">
	<div class="container" style="max-width: 400px;">
		<div class="card shadow-sm">
			<div class="card-header bg-primary text-white text-center fw-bold">
				ĐỔI MẬT KHẨU</div>
			<div class="card-body p-4">
				<c:if test="${not empty message}">
					<div
						class="alert alert-${messageType == 'success' ? 'success' : 'danger'}">
						${message}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/changePassword"
					method="post">
					<div class="mb-3">
						<label class="form-label">Mật khẩu hiện tại</label> <input
							type="password" name="currentPass" class="form-control" required>
					</div>
					<div class="mb-3">
						<label class="form-label">Mật khẩu mới</label> <input
							type="password" name="newPass" class="form-control" required>
					</div>
					<div class="mb-3">
						<label class="form-label">Xác nhận mật khẩu mới</label> <input
							type="password" name="confirmPass" class="form-control" required>
					</div>
					<div class="d-grid gap-2">
						<button type="submit" class="btn btn-primary fw-bold">Lưu
							Thay Đổi</button>
						<a href="${pageContext.request.contextPath}/home"
							class="btn btn-outline-secondary">Về Trang Chủ</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>