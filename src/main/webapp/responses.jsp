<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 23.05.2015
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title>Responses</title>
</head>
<body>
<header>
  <jsp:include page="header.jsp"/>
</header>
<div align="center">
<table>
  <c:forEach var="response" items="${responses}">
    <tr>
      <td width="90%"><b><i>${response.user.login}</i></b></td>
      <td align="right"><i>${response.dateCreated}</i></td>
    </tr>
    <tr>
      <td colspan="2">${response.text}</td>
    </tr>
  </c:forEach>
</table>
    <sec:authorize access="hasRole('CLIENT')">
        <form:form method="post" action="/comments" commandName="response">
            <table>
                <tr>
                    <td>Text*:</td>
                    <td><form:textarea cols="80" rows="5" path="text"/></td>
                    <td><form:errors path="text" cssStyle="color: #ff0000;"/></td>
                </tr>
                <tr>
                    <td colspan="3" align="right">
                        <input type="submit" value="Save">
                    </td>
                </tr>
            </table>
        </form:form>
    </sec:authorize>
</div>
</body>
</html>
