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
<a href="${pageContext.request.contextPath}/admin/video/details">Video Details</a> |
<a href="${pageContext.request.contextPath}/admin/video/list">Video List</a> |
</div>
<div>
	<jsp:include page="${subpage}"></jsp:include>
</div>
</body>
</html>