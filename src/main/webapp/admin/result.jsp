<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title>Result</title>
</head>
<body>
<header>
  <jsp:include page="../header.jsp"/>
</header>
<h1><c:out value="${result_message}"/></h1>
</body>
</html>
