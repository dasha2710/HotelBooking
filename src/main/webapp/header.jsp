<%--
  Created by IntelliJ IDEA.
  User: Dasha
  Date: 03.05.2015
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
    <div id="header">
        <div align="right">
            <sec:authorize access="isAnonymous()">
                <a href="login"><input type="submit" value="Login" ></a>
                <a href="register"><input type="submit" value="Register"></a>
            </sec:authorize>
            <sec:authorize access="hasRole('CLIENT')">
                <a href="logout"><input type="submit" value="Log out"></a>
                <a href="/client/office"><input type="submit" value="Personal office"></a>
            </sec:authorize>
        </div>
        <div align="center" id="links">
            <a href="/index">About us</a>
            <a href="/apartments">Rooms</a>
            <a href="/booking">OnlineBooking</a>
            <a href="#">Comments</a>
            <a href="/contacts">Contacts</a>
        </div>
    </div>
</body>
</html>
