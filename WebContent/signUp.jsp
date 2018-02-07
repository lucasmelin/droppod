<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up</title>
</head>
<body>
<form action="signupServlet" method="post">
	        <fieldset style="width: 300px">
	            <legend>Register an account</legend>
	            <table>
	                <tr>
	                    <td>Username</td>
	                    <td><input type="text" name="username" id="username" required="required" /></td>
	                </tr>
	                <tr>
	                    <td>Password</td>
	                    <td><input type="password" name="password" id="password" required="required" /></td>
	                </tr>
	                <tr>
	                    <td>Email</td>
	                    <td><input type="email" name="email" id="email" required="required" /></td>
	                </tr>
	                <tr>
	                    <td>
	                    <input type="submit" class="button" style="font-size: 14px; line-height:10px; width: 100%;" value="Register" /></td>
	                </tr>
	            </table>
	        </fieldset>
	    </form>
</body>
</html>