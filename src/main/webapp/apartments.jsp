<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19.04.2015
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div align="center">
  <table>
    <tr>
      <th>Apartment type</th>
      <th>Capacity (people)</th>
      <th>Description</th>
      <th>Price ($/24h)</th>
      <th></th>
    </tr>
    <c:forEach var="category" items="${apartments}">
      <tr>
        <td>${category.type}</td>
        <td>${category.capacity}</td>
        <td>${category.description}</td>
        <td>${category.price}</td>
        <td><a href="#">More...</a> </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
