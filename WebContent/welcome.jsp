<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sql:query var="rs" dataSource="jdbc/droppod">
select name, thumbnail_url, uuid from droppod.podcasts
</sql:query>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />

<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
<html lang="${language}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="welcome"/> <%=session.getAttribute("name")%></title>
<link rel='stylesheet prefetch'
	href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h3>Login successful!!!</h3>
	<h4>
		<fmt:message key="hello"/>,
		<%=session.getAttribute("name")%></h4>

	<c:forEach var="row" items="${rs.rows}">
		<div class="image" style="width:130px; height:130px;">
			<a href="${pageContext.request.contextPath}/podcastServlet?uuid=${row.uuid}">
			<img src="${row.thumbnail_url}" alt="${row.name}" style="width: 100%; height: 100%; border-radius: 3px;">
			</a>
		</div>
	</c:forEach>
	
	<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
<script src="js/welcome.js"></script>
</body>
</fmt:bundle>
</html>