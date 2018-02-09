<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<<<<<<< HEAD
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="css/navbar.css">

    <title>DropPod</title>
  </head>
  <body>
	<nav class="navbar navbar-dark bg-mint sticky-top flex-md-nowrap p-0">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0">DropPod</a>
    	<input class="form-control form-control-mint w-100" type="text" placeholder="Search" aria-label="Search">
    	<ul class="navbar-nav px-3">
	        <li class="nav-item text-nowrap">
	        	<a class="nav-link" href="#">Sign out</a>
	        </li>
    	</ul>
	</nav>

	<div class="container-fluid">
		<div class="row no-gutter">
			<nav class="col-md-2 sidebar">
				<div class="sidebar-sticky">
					<ul class="nav flex-column">
						<li class="nav-item">
							<a class="nav-link active" href="#">
								<span data-feather="home"></span>
								Casts
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">
								<span data-feather="home"></span>
								Following
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">
								<span data-feather="home"></span>
								Popular
							</a>
						</li>
					</ul>
				</div>
			</nav>
		</div>
	</div>
	
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script>
      feather.replace()
    </script>
  </body>
=======
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
<html lang="${language}">

<head>
<link rel="stylesheet" type="text/css" href="StyleSheet.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Application</title>
</head>
<body>
<img src="./Assets/Logo.png" height="20%" style="display: block; margin-left: auto; margin-right: auto; margin-bottom: 5%; margin-top: 10%;">
    <div id="sign-in">
	    <form action="loginServlet" method="post">
	        <fieldset style="width: 300px">
	            <legend> Login to DropPod </legend>
	            <table>
	                <tr>
	                    <td><fmt:message key="userid"/></td>
	                    <td><input type="text" name="username" id="username" required="required" /></td>
	                </tr>
	                <tr>
	                    <td><fmt:message key="password"/></td>
	                    <td><input type="password" name="password" id="password" required="required" /></td>
	                </tr>
	                <tr>
	                    <td>
	                    <input type="submit" class="button" style="font-size: 14px; line-height:10px; width: 100%;" value="<fmt:message key="login"/>" /></td>
	                </tr>
	            </table>
	        </fieldset>
	    </form>
    </div>
    <form>
    <select id="language" name="language" style="background-color: #003399; color: #f2f2f2" onchange="submit()">
      <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
      <option value="fr" ${language == 'fr' ? 'selected' : ''}>Francais</option>
    </select>
</form>
    
    
</body>
</fmt:bundle>
>>>>>>> 1e090303a533a8c78cd7954975233eda463b92f8
</html>
