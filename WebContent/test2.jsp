<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1> test2.jsp </h1>

<h2>request.contextPath = ${request.contextPath}</h2>
<h2>response.contentType = ${response.contentType}</h2>

<h2>pageContext.request.contextPath = ${pageContext.request.contextPath}</h2>
<h2>pageContext.response.contentType = ${pageContext.response.contentType}</h2>

<%
session.getId();
%>

<h2>pageContext.session.id = ${pageContext.session.id}</h2>

<h2> <%= application.getRealPath("") %> </h2>

</body>
</html>