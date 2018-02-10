<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="StyleSheet.css" />
	
	<style>
	}
	</style>
<title>Sign up</title>
</head>
<body>
<img src="./Assets/DropPod_Crop.png" height="50%"style="display: block; margin-left: auto; margin-right: auto; margin-bottom: 1%; margin-top: 2%;">
	
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
					<td><input type="email" name="email" id="email"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password"></td>
				</tr>
				<tr>
					<td>Repeat Password:</td>
					<td><input type="password" name="repassword" id="repassword"></td>
				</tr>
				<tr>
				
				</tr>
			</table>
			<br>
			<td><input type="submit" class="button" style="font-size: 14px; line-height:10px; width: 100%;" value="Sign Up" /></td>
			<br><br>		
	    </form>
	    </div>
</body>
</html>