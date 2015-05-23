<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title>Personal office</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<div align="right">
    <a href="/client/change_pass.jsp">Change password</a>
</div>
<div align="center">
    <h1>Hi, ${user.client.name} ${user.client.surname}! There is all orders you have:</h1>
    <form:form action="/client/cancelOrder" method="post">
        <table>
            <tr>
                <th class="dates">Order dates</th>
                <th class="picture">Photo</th>
                <th class="type">Type</th>
                <th class="description">Description</th>
                <th class="price">Total price</th>
                <th class="status">Status</th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${user.client.ordersCollection}">
                <tr>
                    <td>${order.dateCheckIn} - ${order.dateCheckOut}</td>
                    <td><img width="100%" src="${order.room.category.mainPicture.path}"/></td>
                    <td>${order.room.category.type}</td>
                    <td>${order.room.category.description}</td>
                    <td>${order.totalPrice}</td>
                    <td>${order.status.type}</td>
                    <c:if test="${order.status.type ne 'CANCELLED'}">
                        <td>
                            <button type='submit' value="${order.id}" name="order_id"
                                    onclick="return confirm('Are you sure you want to cancel this order?')">Cancel</button>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</div>
</body>
</html>
