<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dasha
  Date: 21.05.2015
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title>Admins' office</title>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<h1>Hi, ${user.login}</h1>
</body>
</html>
