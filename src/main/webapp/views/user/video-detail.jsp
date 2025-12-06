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
        
        <div class="mb-3 d-flex gap-2">
    <a href="${pageContext.request.contextPath}/video/like?id=${video.id}" 
       class="btn ${isLiked ? 'btn-danger' : 'btn-outline-danger'}">
       <i class="bi bi-heart${isLiked ? '-fill' : ''} me-2"></i> 
       ${isLiked ? 'Đã thích' : 'Thích'}
    </a>

    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#shareModal">
        <i class="bi bi-share me-2"></i>Chia sẻ
    </button>
</div>

<div class="modal fade" id="shareModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/video/share" method="post">
          <div class="modal-header">
            <h5 class="modal-title">Chia sẻ video qua Email</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <input type="hidden" name="videoId" value="${video.id}">
            <div class="mb-3">
                <label>Email người nhận:</label>
                <input type="email" name="email" class="form-control" required placeholder="name@example.com">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            <button type="submit" class="btn btn-primary">Gửi</button>
          </div>
      </form>
    </div>
  </div>
</div>
        
        <h4>Mô tả</h4>
        <p>${video.description}</p>
        
        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary mt-3">Quay về Home</a>
    </c:if>
</div>