<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Dasha
  Date: 04.05.2015
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title>Forgot your password</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<div align="center">
    <form:form method="post" commandName="user">
        <table border="0">
            <tr>
                <td colspan="2" align="center">
                    <h2>Please enter your login and generated password will be sent to your email</h2>
                </td>
            </tr>
            <tr>
                <td>Enter your login:</td>
                <td><form:input path="login"/></td>
                <td><form:errors path="login" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Send new password"/></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
