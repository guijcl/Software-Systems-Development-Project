<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.SingleSaleModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>BilGes: Choose Single Date</title>
</head>
<body>
<h2>Choose Single Date</h2>
<form action="chooseSingleSeats" method="post">
	<div class="mandatory_field">
		<label for="date">Dates:</label>
		<select name="date">
			<c:forEach var="period" items="${model.periods}">
				<c:choose>
    				<c:when test="${model.dates == period.date}">
						<option selected = "selected" value="${period.date}">${period.date}</option>
				    </c:when>
    				<c:otherwise>
						<option value="${period.date}">${period.date}</option>
				    </c:otherwise>
				</c:choose>
			</c:forEach> 
		</select>
   </div>

   <div class="button" align="right">
   		<input type="submit" value="Choose Single Date">
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
