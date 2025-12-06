<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="container my-4">
    <h2 class="text-danger mb-4">Video Yêu Thích Của Tôi</h2>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="fav" items="${favList}">
            <div class="col">
               <div class="card h-100" onclick="location.href='${pageContext.request.contextPath}/video/detail?id=${fav.video.id}'" style="cursor:pointer">
                    <img src="${fav.video.poster}" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">${fav.video.title}</h5>
                        <p class="card-text text-muted">Ngày like: ${fav.likeDate}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
         <c:if test="${empty favList}">
            <p>Bạn chưa yêu thích video nào.</p>
        </c:if>
    </div>
</div>