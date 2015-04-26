<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>

<body>
<div align="center">

    <form id="form" action="<c:url value='/login.do'/>" method="POST">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Please enter your login and password:</h2></td>
            </tr>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="login" value=""/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" value=""/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <c:if test="${not empty param.err}">
                        <div><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></div>
                    </c:if>
                    <c:if test="${not empty param.out}">
                        <div>You've logged out successfully.</div>
                    </c:if>
                    <c:if test="${not empty param.time}">
                        <div>You've been logged out due to inactivity.</div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input value="Login" name="submit" type="submit"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>