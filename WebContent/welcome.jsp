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
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/navbar.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DropPod</title>
<link rel='stylesheet prefetch'
	href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>

</head>
<body>
	<nav class="navbar navbar-dark bg-mint sticky-top flex-md-nowrap p-0">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0">DropPod</a> <input
			class="form-control form-control-mint w-100" type="text"
			placeholder="Search" aria-label="Search">
		<ul class="navbar-nav px-3">
			<li class="nav-item text-nowrap"><a class="nav-link" href="#"><fmt:message key="welcome.signout" /></a></li>
		</ul>
	</nav>

	<div class="container-fluid">
		<div class="row no-gutter">
			<nav class="col-md-2 sidebar">
				<div class="sidebar-sticky">
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link" href="#">
								<span data-feather="home"></span><fmt:message key="welcome.signedinas" />: <%=session.getAttribute("name")%> 
						</a></li>
						<li class="nav-item"><a class="nav-link active" href="#">
								<span data-feather="home"></span><fmt:message key="welcome.casts" />
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="home"></span><fmt:message key="welcome.following" />
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="home"></span><fmt:message key="welcome.popular" />
						</a></li>
					</ul>
				</div>
			</nav>
		</div>
	</div>


	<div class="container">
		<div class="row">
			<c:forEach var="row" items="${rs.rows}" varStatus="loopStatus">
				<c:if test="${loopStatus.index % 4 == 0}">
		</div>
		<div class="row">
			</c:if>
			<div class="image col" style="width: 130px; height: 100%;">
				<a
					href="${pageContext.request.contextPath}/podcastServlet?uuid=${row.uuid}">
					<img src="${row.thumbnail_url}" alt="${row.name}"
					style="width: 100%; height: 100%; border-radius: 3px;">
				</a>
			</div>
			</c:forEach>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
	<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
	<script> 
      feather.replace() 
    </script>
	<script src="js/welcome.js"></script>
</body>
</body>
</fmt:bundle>
</html>