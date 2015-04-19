<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Daryna_Ragimova
  Date: 4/16/2015
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div align="center">
    <form:form method="post" commandName="user">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Registration</h2></td>
            </tr>
            <tr>
                <td>Surname:</td>
                <td><form:input path="client.surname" /></td>
                <td><form:errors path="client.surname" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><form:input path="client.name" /></td>
                <td><form:errors path="client.name" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>BirthDate:</td>
                <td><form:input path="client.birthday" /></td>
                <td><form:errors path="client.birthday" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><form:input path="client.phone" /></td>
                <td><form:errors path="client.phone" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><form:input path="client.email" /></td>
                <td><form:errors path="client.email" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Passport:</td>
                <td><form:input path="client.passport" /></td>
                <td><form:errors path="client.passport" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Login:</td>
                <td><form:input path="login" /></td>
                <td><form:errors path="login" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><form:password path="password" /></td>
                <td><form:errors path="password" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Confirm password:</td>
                <td><form:password path="matchingPassword" /></td>
                <td><form:errors path="matchingPassword" cssStyle="color: #ff0000;"/></td>
            </tr>

            <tr>
                <td colspan="2" align="center"><input type="submit" value="Register" /></td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>
