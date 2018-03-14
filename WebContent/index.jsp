<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
	<div class="form-login">
	<form class="" action="loginServlet" method="post">
		<img class="mb-1" src="./Assets/Logo.png" height="200px" width="175px">
		<h3 class="mb-3 font-weight-normal"><fmt:message key="index.logintodroppod" /></h3>
		<div class="form-group">
			<c:if test="${success == \"false\"}" >
				<div class="alert alert-danger" role="alert"><strong>Invalid username or password</strong></div>
			</c:if>
			<input class="form-control" type="text" name="username" id="username" placeholder="<fmt:message key="index.userid" />" required autofocus />
			<input class="form-control" type="password" name="password" id="password" placeholder="<fmt:message key="index.password" />" required />
		</div>
		<input type="submit" class="btn btn-dark btn-lg btn-block mb-3" value="<fmt:message key="index.login"/>" />
	</form>
	<form action="setLanguageServlet" method="get">
		<div class="btn-group">
			<a class="btn btn-small btn-outline-dark btn-block" href="${pageContext.request.contextPath}/signUp.jsp"><fmt:message key="index.createAccount" /></a>
			
			
			<div class="btn-group" role="group">
			    <select class="btn btn-outline-dark dropdown-toggle" aria-labelledby="btnGroupDrop1" id="language" name="language"
				onchange="submit()">
			      <option class="dropdown-item" value="en" ${language == 'en' ? 'selected' : ''}>English</option>
			      <option class="dropdown-item" value="fr" ${language == 'fr' ? 'selected' : ''}>Français</option>
			    </select>
			</div>
			
			
		</div>
		</form>
		
	</div>
</body>
</fmt:bundle>
</html>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>