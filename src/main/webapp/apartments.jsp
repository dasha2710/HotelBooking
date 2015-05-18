<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19.04.2015
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title></title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<div align="center">
    <table>
        <tr>
            <th class="picture">Photo</th>
            <th class="type">Apartment type</th>
            <th class="capacity">Capacity (people)</th>
            <th class="description">Description</th>
            <th class="price">Price ($/24h)</th>
            <th></th>
        </tr>
        <c:forEach var="category" items="${apartments}">
            <tr>
                <td><img width="100%" src="${category.mainPicture.path}"/></td>
                <td>${category.type}</td>
                <td>${category.capacity}</td>
                <td>${category.description}</td>
                <td>${category.price}</td>
                <td><a href="/apartments/${category.id}">More...</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
