<%--
  Created by IntelliJ IDEA.
  User: Dasha
  Date: 23.05.2015
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
type="text/css"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<html>
<head>
    <title>Order information</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<script>
    $(document).ready(function () {
        $("#new_date_check_out").datepicker({dateFormat: 'mm/dd/yy', minDate: ${dayShift}});
    });
    </script>
<form:form method="post">
    <div align="center">
        <table border="0">
            <td>New date check out:</td>
            <td><input type="text" id="new_date_check_out" name="new_date_check_out" placeholder="mm/dd/yyyy"></td>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Prolong"/></td>
            </tr>
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
</form:form>
</body>
</html>
