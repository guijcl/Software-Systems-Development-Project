<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.PassSaleModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>BilGes: Pass Ticket Sale Info</title>
</head>
<body>
<h2>Sale Info</h2>
	<p>Event Name: ${model.eventName}</p>
	<p>Quantity: ${model.quantity}</p>
	<p>Email: ${model.email}</p>
	<p>Price: ${model.price}</p>
<c:if test="${model.hasMessages}">
	<p>Messages</p>
	<ul>
	<c:forEach var="mensagem" items="${model.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
</body>
</html>