<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
<html lang="${language}">

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="StyleSheet.css" />
	<title>Sign up</title>
</head>

<body>
	<div id="login-image"> <img src="DropPod_Crop.png" height="50%"> </div>
	<div id="sign-in">
		<form action="signupServlet" method="post">
	        <fieldset style="width: 300px">
	            <legend>Register an account</legend>
	            <table>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" id="username"></td>
				</tr>
				<tr>
					<td>Email Address:</td>
					<td><input type="text" name="email" id="email"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password"></td>
				</tr>
				<tr>
					<td>Repeat Password:</td>
					<td><input type="password" name="repassword" id="repassword"></td>
				</tr>
			</table>
			<br>
			<td><input type="submit" class="button" style="font-size: 14px; line-height:10px; width: 100%;" value="Sign Up" /></td>
			<br><br>		
	    </form>
	    </div>
</body>
</fmt:bundle>
</html>