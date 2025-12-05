<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Danh sách Video</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body { background-color: #fff0f5; }
    .video-card {
        cursor: pointer;
        transition: 0.2s;
        border-radius: 10px;
        background-color: #ffe6f0;
        padding: 10px;
        text-align: center;
    }
    .video-card:hover {
        transform: scale(1.03);
        box-shadow: 0 8px 20px rgba(255, 105, 180, 0.3);
    }
    .video-container img {
        width: 100%;
        border-radius: 10px;
    }
</style>
</head>

<body>
<div class="container my-5">
    <h1 class="text-center text-primary fw-bold mb-4">Danh sách Video</h1>

    <div class="row row-cols-1 row-cols-md-3 g-4">

        <c:forEach var="v" items="${videoList}">

            <!-- Tách YouTube ID từ link -->
            <c:choose>
                <c:when test="${fn:contains(v.link, 'youtu.be/')}">
                    <c:set var="ytId" value="${fn:substringAfter(v.link, 'youtu.be/')}" />
                </c:when>

                <c:when test="${fn:contains(v.link, 'v=')}">
                    <c:set var="ytId" value="${fn:substringAfter(v.link, 'v=')}" />
                </c:when>

                <c:otherwise>
                    <c:set var="ytId" value="" />
                </c:otherwise>
            </c:choose>

            <div class="col">
               <div class="video-card" onclick="location.href='video/detail?id=${v.id}'">

                    <div class="video-container">
                        <img src="https://img.youtube.com/vi/${ytId}/hqdefault.jpg">
                    </div>

                    <div class="video-title">${v.title}</div>

                    <button class="btn btn-outline-danger btn-sm">Like</button>
                    <button class="btn btn-outline-primary btn-sm">Share</button>
                </div>
            </div>

        </c:forEach>

    </div>

</div>

<script>
function playVideo(id) {
    if(!id) return;
    window.open("https://www.youtube.com/watch?v=" + id, "_blank");
}
</script>

</body>
</html>
