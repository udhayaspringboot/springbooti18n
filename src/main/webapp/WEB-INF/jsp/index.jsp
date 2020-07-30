<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

HI <spring:message code="label.message" /> in <a href="?lang=en"> <spring:message code="lang.en" /> </a>nbsp <a href="?lang=de"><spring:message code="lang.de" /></a>

   ${home.name }
</body>
</html>