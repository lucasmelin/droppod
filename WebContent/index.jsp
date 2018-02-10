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
<link rel="stylesheet" type="text/css" href="StyleSheet.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="index.logintodroppod" /></title>
</head>
<body>
	<img src="./Assets/Logo.png" height="20%"
		style="display: block; margin-left: auto; margin-right: auto; margin-bottom: 5%; margin-top: 10%;">
	<div id="sign-in">
		<form action="loginServlet" method="post">
			<fieldset style="width: 300px">
				<legend>
					<fmt:message key="index.logintodroppod" />
				</legend>
				<table>
					<tr>
						<td><fmt:message key="index.userid" /></td>
						<td><input type="text" name="username" id="username"
							required="required" /></td>
					</tr>
					<tr>
						<td><fmt:message key="index.password" /></td>
						<td><input type="password" name="password" id="password"
							required="required" /></td>
					</tr>
					<tr>
						<td><input type="submit" class="button"
							style="font-size: 14px; line-height: 10px; width: 100%;"
							value="<fmt:message key="index.login"/>" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
		<p>
			<fmt:message key="index.noaccount" /> <a
				href="${pageContext.request.contextPath}/signUp.jsp"><fmt:message key="index.clickhere" /></a>
		</p>
		<form>
			<select id="language" name="language"
				style="background-color: #003399; color: #f2f2f2"
				onchange="submit()">
				<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
				<option value="fr" ${language == 'fr' ? 'selected' : ''}>Français</option>
			</select>
		</form>
	</div>

</body>
</fmt:bundle>
</html>
