<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h2>request.contextPath = ${request.contextPath}</h2>
<h2>response.contentType = ${response.contentType}</h2>

<h2>pageContext.request.contextPath = ${pageContext.request.contextPath}</h2>
<h2>pageContext.response.contentType = ${pageContext.response.contentType}</h2>

<%
%>
<h2>pageContext.servletContext.serverInfo = ${pageContext.servletContext.serverInfo}</h2>
<h2>pageContext.servletContext.majorVersion = ${pageContext.servletContext.majorVersion}</h2>
<h2>pageContext.servletContext.minorVersion = ${pageContext.servletContext.minorVersion}</h2>

</body>
</html>