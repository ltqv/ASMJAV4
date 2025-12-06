<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>VideoManager</h1>
<div>
<a href="${pageContext.request.contextPath}/admin/video/details" class="btn btn-primary me-2"><i class="bi bi-plus me-1"></i> Thêm Video Mới</a> |
<a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-info text-white"><i class="bi bi-list me-1"></i> Danh Sách Video</a>
</div>
<hr>
<div>
	<jsp:include page="${subpage}"></jsp:include>
</div>
</body>
</html>