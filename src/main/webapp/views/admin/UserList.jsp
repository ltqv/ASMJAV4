<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>Danh sách Người dùng</h3>

<table class="table table-bordered table-striped mt-3">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Fullname</th>
            <th>Email</th>
            <th>Admin</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.fullname}</td>
            <td>${u.email}</td>
            <td>${u.admin ? 'Có' : 'Không'}</td>
            <td>
                <%-- Giả định có EditServlet: --%>
                <a href="${pageContext.request.contextPath}/admin/user/details?id=${u.id}" class="btn btn-sm btn-warning">
                    <i class="bi bi-pencil me-1"></i> Sửa
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${empty users}">
    <div class="alert alert-info">Không có người dùng nào được tìm thấy.</div>
</c:if>