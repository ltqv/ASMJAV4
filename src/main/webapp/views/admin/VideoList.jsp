<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty message}">
    <div class="alert alert-${messageType == 'success' ? 'success' : 'danger'}">
        ${message}
    </div>
</c:if>

<table class="table table-bordered table-striped mt-3">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Poster</th>
            <th>Views</th>
            <th>Active</th>
            <th>Link</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="v" items="${videos}">
        <tr>
            <td>${v.id}</td>
            <td>${v.title}</td>
            <td><img src="${v.poster}" width="80"></td>
            <td>${v.views}</td>
            <td>${v.active}</td>
            <td><a href="${v.link}" target="_blank">Link</a></td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/video/details?id=${v.id}" class="btn btn-sm btn-warning">
                    <i class="bi bi-pencil me-1"></i> Sửa
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${empty videos}">
    <div class="alert alert-info">Không có video nào được tìm thấy.</div>
</c:if>