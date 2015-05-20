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
    <title>Personal office</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<div align="right">
    <a href="change_pass.jsp">Change password</a>
</div>
<div align="center">
    <h1>Hi, ${user.client.name} ${user.client.surname}! There is all orders you have:</h1>
    <table>
        <tr>
            <th class="dates">Order dates</th>
            <th class="picture">Photo</th>
            <th class="type">Type</th>
            <th class="description">Description</th>
            <th class="price">Total price</th>
            <th></th>
        </tr>
        <c:forEach var="order" items="${user.client.ordersCollection}">
            <tr>
                <td>${order.dateCheckIn} - ${order.dateCheckOut}</td>
                <td><img src="${order.room.category.mainPicture.path}"/></td>
                <td>${order.room.category.type}</td>
                <td>${order.room.category.description}</td>
                <td>${order.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
