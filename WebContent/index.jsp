<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
	<html lang="${language}">

<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/navbar.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="index.logintodroppod" /></title>
</head>
<body class="text-center bg-mint center-body">
	<form class="form-login" action="loginServlet" method="post">
		<img class="mb-4" src="./Assets/Logo.png" height="100px" width="100px">
		<h3 class="mb-3 font-weight-normal"><fmt:message key="index.logintodroppod" /></h3>
		<div class="form-group">
			<input class="form-control" type="text" name="username" id="username" placeholder="<fmt:message key="index.userid" />" required autofocus />
			<input class="form-control" type="password" name="password" id="password" placeholder="<fmt:message key="index.password" />" required />
			<c:if test="${success == \"false\"}" >
				<div class="alert alert-danger" role="alert"><strong>Invalid username or password</strong></div>
			</c:if>
		</div>
		<input type="submit" class="btn btn-secondary btn-lg btn-block" value="<fmt:message key="index.login"/>" />
	</form>
</body>
</fmt:bundle>
</html>
