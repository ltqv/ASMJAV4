<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Poster</th>
        <th>Views</th>
        <th>Description</th>
        <th>Active</th>
        <th>Link</th>
    </tr>

    <c:forEach var="v" items="${videos}">
        <tr>
            <td>${v.id}</td>
            <td>${v.title}</td>
            <td><img src="${v.poster}" width="80"></td>
            <td>${v.views}</td>
            <td>${v.description}</td>
            <td>${v.active}</td>
            <td>${v.link}</td>
        </tr>
    </c:forEach>
</table>
