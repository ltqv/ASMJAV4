<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${video.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <h2 class="mb-3">${video.title}</h2>
    <p class="text-muted">${video.views} lượt xem</p>
    <div class="ratio ratio-16x9 mb-3">
        <iframe src="${video.link}" title="${video.title}" allowfullscreen></iframe>
    </div>
    <p>${video.description}</p>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Quay về Home</a>
</div>
</body>
</html>
