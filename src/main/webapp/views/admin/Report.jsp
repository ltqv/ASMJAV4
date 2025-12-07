<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="mb-4">Thống Kê & Báo Cáo</h2>

<ul class="nav nav-tabs mb-3">
  <li class="nav-item">
    <a class="nav-link ${empty showTab ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/report">Tổng Hợp Lượt Thích</a>
  </li>
  <c:if test="${not empty showTab}">
      <li class="nav-item">
        <a class="nav-link active" href="#">Chi Tiết Video</a>
      </li>
  </c:if>
</ul>

<c:if test="${empty showTab}">
    <div class="card shadow-sm">
        <div class="card-body">
            <h5 class="card-title text-success">Thống kê lượt thích theo Video</h5>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Tên Video</th>
                        <th>Số Lượt Thích</th>
                        <th>Chức năng</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${likeStats}">
                        <tr>
                            <td>${item[0]}</td>
                            <td class="fw-bold text-danger">${item[1]}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/report?type=likes&id=${item[2]}" 
                                   class="btn btn-sm btn-info text-white">
                                   <i class="bi bi-people"></i> Người Thích
                                </a>
                                <a href="${pageContext.request.contextPath}/admin/report?type=shares&id=${item[2]}" 
                                   class="btn btn-sm btn-primary">
                                   <i class="bi bi-share"></i> Người Chia Sẻ
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${showTab == 'likes'}">
    <div class="alert alert-info">
        Danh sách người dùng đã thích video: <strong>${videoTitle}</strong>
    </div>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Username</th>
                <th>Họ tên</th>
                <th>Email</th>
                <th>Ngày Thích</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="fav" items="${favUsers}">
                <tr>
                    <td>${fav.user.id}</td>
                    <td>${fav.user.fullname}</td>
                    <td>${fav.user.email}</td>
                    <td>${fav.likeDate}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty favUsers}">
                <tr><td colspan="4" class="text-center">Chưa có ai thích video này.</td></tr>
            </c:if>
        </tbody>
    </table>
</c:if>

<c:if test="${showTab == 'shares'}">
    <div class="alert alert-primary">
        Danh sách chia sẻ của video: <strong>${videoTitle}</strong>
    </div>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Người Gửi (Username)</th>
                <th>Người Gửi (Email)</th>
                <th>Người Nhận (Email)</th>
                <th>Ngày Gửi</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="share" items="${shareList}">
                <tr>
                    <td>${share.user.id}</td>
                    <td>${share.user.email}</td>
                    <td>${share.emails}</td>
                    <td>${share.shareDate}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty shareList}">
                <tr><td colspan="4" class="text-center">Video này chưa được chia sẻ.</td></tr>
            </c:if>
        </tbody>
    </table>
</c:if>