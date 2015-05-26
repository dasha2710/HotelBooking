<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
  <title>Changing password</title>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
        type="text/css"/>
</head>
<body>
<header>
  <jsp:include page="../header.jsp"/>
</header>

<form:form action="/client/update_pass" method="post">
  <div align="center">
    <table><tr>
      <td>Current password:</td>
      <td><input type="password" id="oldPass" name="oldPass"></td>
      </tr>
      <tr>
      <td>New password:</td>
      <td><input type="password" id="newPass" name="newPass"></td>
      <tr>
        <td colspan="2" align="center"><input type="submit" value="Change password"/></td>
      </tr>
      <tr>
        <c:out value="${result_message}"/>
      </tr>
</form:form>

</body>
</html>
