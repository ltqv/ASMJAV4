<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="mb-4">
    <a href="${pageContext.request.contextPath}/admin/report?view=likes" class="btn btn-primary me-2">Thống kê Lượt Thích</a>
    <a href="${pageContext.request.contextPath}/admin/report?view=shares" class="btn btn-primary">Thống kê Lượt Chia Sẻ (Cần triển khai logic)</a>
</div>
<hr>

<%-- Báo cáo Lượt Thích --%>
<c:if test="${param.view eq 'likes'}">
    <h3>Thống kê Số Người Yêu Thích Từng Video</h3>
    <table class="table table-bordered table-striped mt-3">
        <thead class="table-dark">
            <tr>
                <th>ID Video</th>
                <th>Tiêu đề</th>
                <th>Tổng số lượt thích</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${reportLikes}">
            <tr>
                <td>${item[0]}</td>
                <td>${item[1]}</td>
                <td>${item[2]}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/report?view=users&videoId=${item[0]}" class="btn btn-sm btn-info text-white">
                        <i class="bi bi-people"></i> Xem người thích
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<%-- Lọc Người Yêu Thích Từng Video --%>
<c:if test="${param.view eq 'users' and not empty usersLiked}">
    <h3>Danh sách Người dùng Thích Video: ${param.videoId}</h3>
    <table class="table table-bordered table-striped mt-3">
        <thead class="table-dark">
            <tr>
                <th>Tên người dùng</th>
                <th>Email</th>
                <th>Ngày thích</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="fav" items="${usersLiked}">
            <tr>
                <td>${fav.user.fullname}</td>
                <td>${fav.user.email}</td>
                <td>${fav.likeDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>