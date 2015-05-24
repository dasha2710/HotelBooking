<%--
  Created by IntelliJ IDEA.
  User: Dasha
  Date: 23.05.2015
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
<html>
<head>
    <title>Order information</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<div align="center">
    <c:if test="${order.status.type eq 'BOOKED'}">
        <c:if test="${order.dateCheckIn eq curDate}">
            <a href="/admin/orders/${order.id}/check_in">Check in</a>
        </c:if>
        <a href="/admin/orders/${order.id}/cancel">Cancel order</a>
    </c:if>
    <c:if test="${order.status.type eq 'SETTLED'}">
        <a href="/admin/orders/${order.id}/check_out">Check out</a>
        <a href="/admin/orders/${order.id}/prolong" methods="GET">Prolong order</a>
    </c:if>
    <h3>${result_message}</h3>
    <table border="0">
        <tr>
            <td colspan="2" align="center"><h2>Client info</h2></td>
        </tr>
        <tr>
            <td>Surname:</td>
            <td>${order.client.surname}</td>
        </tr>
        <tr>
            <td>Name:</td>
            <td>${order.client.name}</td>
        </tr>
        <tr>
            <td>BirthDate:</td>
            <td>${order.client.birthday}</td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td>${order.client.phone}</td>
        </tr>
        <tr>
            <td>Email:</td>
            <td>${order.client.email}</td>
        </tr>
        <tr>
            <td>Passport:</td>
            <td>${order.client.passport}</td>
        </tr>
        <tr>
            <td colspan="2" align="center"><h2>Order info</h2></td>
        </tr>
        <tr>
            <td>Date check in:</td>
            <td><fmt:formatDate value="${order.dateCheckIn}" pattern="MM/dd/yyyy"/></td>
        </tr>
        <tr>
            <td>Date check out:</td>
            <td><fmt:formatDate value="${order.dateCheckOut}" pattern="MM/dd/yyyy"/></td>
        </tr>
        <tr>
            <td>Room number</td>
            <td>${order.room.number}</td>
        </tr>
        <tr>
            <td>Status:</td>
            <td>${order.status.type}</td>
        </tr>
        <tr>
            <td>Total price:</td>
            <td>${order.totalPrice}</td>
        </tr>
    </table>
</div>
</body>
</html>
