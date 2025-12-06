<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="container my-3">
    <c:if test="${empty video}">
        <div class="alert alert-danger">Không tìm thấy video.</div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Về Trang Chủ</a>
    </c:if>
    <c:if test="${not empty video}">
        <h2 class="mb-3">${video.title}</h2>
        <p class="text-muted">${video.views} lượt xem</p>
        
        <c:set var="videoLink" value="${video.link}" />
        
        <%-- Trích xuất YouTube ID cho URL nhúng --%>
        <c:set var="youtubeId" value="" />
        <c:choose>
            <c:when test="${fn:contains(videoLink, 'youtu.be/')}">
                <c:set var="youtubeId" value="${fn:substringAfter(videoLink, 'youtu.be/')}" />
            </c:when>
            <c:when test="${fn:contains(videoLink, 'v=')}">
                <c:set var="youtubeId" value="${fn:substringAfter(videoLink, 'v=')}" />
            </c:when>
            <c:otherwise>
                 <%-- Xử lý trường hợp URL không chuẩn (có thể là lỗi, nhưng vẫn cố gắng tìm ID) --%>
                 <c:set var="youtubeId" value="${fn:substringAfter(videoLink, '/embed/')}" />
            </c:otherwise>
        </c:choose>

        <c:set var="embedUrl" value="https://www.youtube.com/embed/${youtubeId}?autoplay=1" />

        <div class="ratio ratio-16x9 mb-3">
            <%-- Sử dụng URL nhúng để xem trực tiếp --%>
            <iframe src="${embedUrl}" 
                    title="${video.title}" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                    allowfullscreen>
            </iframe>
        </div>
        
        <div class="mb-3">
            <button class="btn btn-danger"><i class="bi bi-heart me-2"></i>Thích</button>
            <button class="btn btn-primary"><i class="bi bi-share me-2"></i>Chia sẻ</button>
        </div>
        
        <h4>Mô tả</h4>
        <p>${video.description}</p>
        
        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary mt-3">Quay về Home</a>
    </c:if>
</div>