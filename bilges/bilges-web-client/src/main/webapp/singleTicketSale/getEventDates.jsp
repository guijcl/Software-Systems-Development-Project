<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.SingleSaleModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>BilGes: Select Event</title>
</head>
<body>
<h2>Select Event</h2>
<form action="chooseSingleDate" method="post">
    <div class="mandatory_field">
    	<label for="event name">Event Name:</label> 
    	<input type="text" name="event name" size="50" value="${model.eventName}"/> 
    </div>
   <div class="button" align="right">
   		<input type="submit" value="Select Event">
   </div>
</form>
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
