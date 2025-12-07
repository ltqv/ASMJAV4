<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="mb-4">Quản Lý Người Dùng</h2>

<div class="table-responsive">
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
            <tr>
                <th>Username</th>
                <th>Họ và Tên</th>
                <th>Email</th>
                <th>Vai trò</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.fullname}</td>
                    <td>${u.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${u.admin}"><span class="badge bg-danger">Admin</span></c:when>
                            <c:otherwise><span class="badge bg-primary">User</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-outline-warning">Sửa</button>
                        <button class="btn btn-sm btn-outline-danger">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>