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
  <title>Apartment details</title>
</head>
<body>
<header>
  <jsp:include page="header.jsp"/>
</header>
<div align="center">
  <table>
      <tr>
        <td width="50%"></td>
        <td>
            <table>
                <tr>
                    <th>Type:</th>
                    <td>${category.type}</td>
                </tr>
                <tr>
                    <th>Price:</th>
                    <td>${category.price}</td>
                </tr>
                <tr>
                    <th>Capacity:</th>
                    <td>${category.capacity}</td>
                </tr>
            </table>
        </td>
      </tr>
      <tr>
          <td colspan="2">${category.description}</td>
      </tr>
      <tr>
          <td colspan="2">${category.details}</td>
      </tr>
  </table>

    <br />

    <div class="thumbnails">
        <c:forEach var="picture" items="${category.picturesCollection}" varStatus="status">
            <img onmouseover="preview.src=img${status.count}.src" name="img${status.count}" src="${picture.path}"/>
        </c:forEach>
    </div><br/>

    <div class="preview" align="center">
        <img name="preview" src="${category.mainPicture.path}"/>
    </div>
</div>
</body>
</html>
