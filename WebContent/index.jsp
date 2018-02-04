<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="StyleSheet.css" />
<title>Login Application</title>
</head>
<body>

	<img src="Logo.png" height="20%" width"150px" style="display: block; margin-left: auto; margin-right: auto; margin-bottom: 5%; margin-top: 10%;">
    
    <form action="loginServlet" method="post">
            <div id="sign-in">
	            <table>
	                <tr>
	                    <td>Username:</td>
	                    <td><input type="text" name="username" required="required" /></td>
	                </tr>
	                <tr>
	                    <td>Password:</td>
	                    <td><input type="password" name="userpass" required="required" /></td>
	                </tr>
	                <tr>
	                    <td><input type="submit" value="Login" /></td>
	                </tr>
	            </table>
            </div>
    </form>
</body>
</html>
