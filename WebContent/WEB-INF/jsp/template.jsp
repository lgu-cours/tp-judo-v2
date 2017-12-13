<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TP Judo Club</title>
<%-- 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
--%>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
</head>

<body>

<table>

	<!-- HEADER --> 
	<tr> 
		<td class="header" colspan="2" > 
		<%@include file="part/header.jspf" %>
		</td> 
	</tr>
	
	<!-- HEADER 2 ( User ) -->
	<tr> 
		<td class="header2" colspan="2" > 
		<%@include file="part/header2.jspf" %>
		</td> 
	</tr>
	
	<!-- MENU and PAGE -->
	<tr> 
		<td class="menu" >
		<%@include file="part/menu.jspf" %>
		</td> 
		<td class="page"> 
		<jsp:include page="/WEB-INF/jsp/page/${requestScope.page}.jsp" />
		</td> 
	</tr>
	
	<!-- MESSAGE -->
	<tr> 
		<td class="message" colspan="2" > 
		<%@include file="part/message.jspf" %>
		</td> 
	</tr>
	
	<!-- FOOTER -->
	<tr> 
		<td class="footer" colspan="2" > 
		<%@include file="part/footer.jspf" %>
		</td> 
	</tr>

</table>

</body>
</html>