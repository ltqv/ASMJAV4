<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Hiển thị thông báo nếu có --%>
<c:if test="${not empty message}">
    <div class="alert alert-${messageType == 'success' ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="card shadow-sm">
    <div class="card-header bg-white border-0">
        <h4 class="mb-0 text-primary"><i class="bi bi-list-ul me-2"></i>Danh Sách Video</h4>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Poster</th>
                        <th>Lượt xem</th>
                        <th>Trạng thái</th>
                        <th>Link</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="v" items="${videos}">
                    <tr>
                        <td class="text-center fw-bold">${v.id}</td>
                        <td>${v.title}</td>
                        <td class="text-center">
                            <img src="${v.poster}" class="rounded shadow-sm" width="100" height="60" style="object-fit: cover;" alt="Poster">
                        </td>
                        <td class="text-center">${v.views}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${v.active}">
                                    <span class="badge bg-success">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Đã ẩn</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <a href="${v.link}" target="_blank" class="btn btn-sm btn-outline-primary">
                                <i class="bi bi-play-circle"></i> Xem
                            </a>
                        </td>
                        <td class="text-center">
                            <div class="btn-group" role="group">
                                <%-- Nút Sửa --%>
                                <a href="${pageContext.request.contextPath}/admin/video/details?id=${v.id}" class="btn btn-sm btn-warning">
                                    <i class="bi bi-pencil-square"></i> Sửa
                                </a>
                                
                                <%-- Nút Xóa (Chỉ hiện nếu đang Active) --%>
                                <c:if test="${v.active}">
                                    <button type="button" class="btn btn-sm btn-danger" onclick="confirmDelete('${v.id}')">
                                        <i class="bi bi-trash"></i> Xóa
                                    </button>
                                </c:if>
                                <%-- Nút Khôi phục (Hiện nếu đã ẩn) --%>
                                <c:if test="${!v.active}">
                                    <button type="button" class="btn btn-sm btn-success" onclick="confirmRestore('${v.id}')">
                                        <i class="bi bi-arrow-counterclockwise"></i> Khôi phục
                                    </button>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        
        <c:if test="${empty videos}">
            <div class="alert alert-info text-center mt-3">
                <i class="bi bi-info-circle me-2"></i>Không có video nào trong danh sách.
            </div>
        </c:if>
    </div>
</div>

<%-- Form ẩn để xử lý Xóa/Khôi phục --%>
<form id="actionForm" action="${pageContext.request.contextPath}/admin/video/crud" method="post">
    <input type="hidden" name="action" id="formAction">
    <input type="hidden" name="id" id="formId">
    <%-- Các trường ẩn khác để tránh lỗi null pointer khi bind entity trong Servlet --%>
    <input type="hidden" name="active" id="formActive">
</form>

<script>
    function confirmDelete(id) {
        if (confirm("Bạn có chắc chắn muốn xóa video này? (Video sẽ bị ẩn khỏi trang người dùng)")) {
            submitAction('delete', id, 'false');
        }
    }

    function confirmRestore(id) {
        if (confirm("Bạn có muốn khôi phục video này không?")) {
          
            submitAction('update', id, 'true');
        }
    }

    function submitAction(action, id, activeState) {
        document.getElementById("formAction").value = action;
        document.getElementById("formId").value = id;
        document.getElementById("formActive").value = activeState; 
        document.getElementById("actionForm").submit();
    }
</script>