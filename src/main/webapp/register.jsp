<!--?xml version="1.0" encoding="UTF-8"?-->
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
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

    <script>
        $(document).ready(function() {
            $("#datepicker").datepicker( {dateFormat: 'mm/dd/yy' });
        });
        function isNumberKey(evt) {
            var charCode = (evt.which) ? evt.which : event.keyCode
            if (charCode > 31 && (charCode < 48 || charCode > 57))
                return false;

            return true;
        }
    </script>
</head>
<body>
<div align="center">
    <form:form method="post" commandName="client">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Registration</h2></td>
            </tr>
            <tr>
                <td>Surname*:</td>
                <td><form:input path="surname" /></td>
                <td><form:errors path="surname" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Name*:</td>
                <td><form:input path="name" /></td>
                <td><form:errors path="name" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>BirthDate:</td>
                <td><form:input path="birthday" id="datepicker" type="text" placeholder="mm/dd/yyyy"/></td>
                <td><form:errors path="birthday" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Phone*:</td>
                <td><form:input path="phone" onkeypress="return isNumberKey(event)" placeholder="380XXXXXXXX"/></td>
                <td><form:errors path="phone" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Email*:</td>
                <td><form:input path="email" placeholder="email@example.com"/></td>
                <td><form:errors path="email" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Passport*:</td>
                <td><form:input path="passport" /></td>
                <td><form:errors path="passport" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Login*:</td>
                <td><form:input path="user.login" /></td>
                <td><form:errors path="user.login" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Password*:</td>
                <td><form:password path="user.password" placeholder="More than 5 symbols"/></td>
                <td><form:errors path="user.password" cssStyle="color: #ff0000;"/></td>
            </tr>
            <tr>
                <td>Confirm password*:</td>
                <td><form:password path="user.matchingPassword" /></td>
                <td><form:errors path="user.matchingPassword" cssStyle="color: #ff0000;"/></td>
            </tr>

            <tr>
                <td colspan="2" align="center"><input type="submit" value="Register" /></td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>
