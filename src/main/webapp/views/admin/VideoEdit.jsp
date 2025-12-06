<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
<c:set var="isEdit" value="${not empty video.id}" />
<c:set var="pageTitle" value="${isEdit ? 'Chỉnh Sửa Video' : 'Thêm Video Mới'}" />

<div class="container my-3">
    <h3 class="mb-4">${pageTitle}</h3>

    <%-- Hiển thị thông báo (flash message) từ session --%>
    <c:if test="${not empty message}">
        <div class="alert alert-${messageType == 'success' ? 'success' : 'danger'}">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/video/crud" method="post">
        
        <c:choose>
            <c:when test="${isEdit}">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${video.id}">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="create">
            </c:otherwise>
        </c:choose>
        
        <div class="mb-3">
            <label class="form-label">ID Video</label>
            <input type="text" name="id" class="form-control" value="${video.id}" 
                   ${isEdit ? 'readonly' : 'required'} 
                   placeholder="${isEdit ? '' : 'Nhập ID video (Không thể đổi sau khi tạo)'}">
        </div>
        
        <div class="mb-3">
            <label class="form-label">Tiêu đề</label>
            <input type="text" name="title" class="form-control" value="${video.title}" required placeholder="Nhập tiêu đề">
        </div>

        <div class="mb-3">
            <label class="form-label">Poster (URL)</label>
            <input type="text" name="poster" class="form-control" value="${video.poster}" placeholder="Nhập URL hình ảnh (Nếu không có, có thể dùng ảnh mặc định)">
        </div>

        <div class="mb-3">
            <label class="form-label">Link YouTube</label>
            <input type="text" name="link" class="form-control" value="${video.link}" required placeholder="Nhập Link YouTube (ví dụ: https://www.youtube.com/watch?v=...)">
        </div>
        
        <div class="mb-3 form-check">
            <input type="checkbox" name="active" value="true" class="form-check-input" id="videoActive" 
                   <c:if test="${isEdit and video.active}">checked</c:if>
                   <c:if test="${!isEdit}">checked</c:if> >
            <label class="form-check-label" for="videoActive">Active (Hiển thị cho người dùng)</label>
        </div>

        <div class="mb-3">
            <label class="form-label">Mô tả</label>
            <textarea name="description" class="form-control" rows="3" placeholder="Nhập mô tả video">${video.description}</textarea>
        </div>

        <div class="d-flex justify-content-between">
            <div>
                <button type="submit" class="btn btn-success me-2">
                    <i class="bi bi-save me-1"></i> ${isEdit ? 'Lưu Thay Đổi' : 'Tạo Mới Video'}
                </button>
                <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-secondary">
                    <i class="bi bi-list me-1"></i> Về Danh Sách
                </a>
            </div>
            <c:if test="${isEdit}">
                <button type="submit" name="action" value="delete" class="btn btn-danger" 
                        onclick="return confirm('Bạn có chắc chắn muốn xóa mềm video này? (Active sẽ được set thành false)')">
                    <i class="bi bi-trash me-1"></i> Xóa Mềm
                </button>
            </c:if>
        </div>

        <c:if test="${isEdit}">
            <p class="mt-3 text-muted">Lượt xem: ${video.views} | ID: ${video.id}</p>
        </c:if>
    </form>
</div>