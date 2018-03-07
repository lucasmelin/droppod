<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />

<c:set var="searchItem" value="${search}" scope="session" />
<c:set var="myVar" value="${param.search}" />

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
<link rel="stylesheet" href="css/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title><fmt:message key="welcome.addapodcast" /></title>
<link rel='stylesheet prefetch'
	href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
</head>
<body>
	<nav class="navbar navbar-dark bg-mint sticky-top flex-md-nowrap p-0">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0">DropPod</a>
		<form class="form-inline w-100 my-2 my-lg-0" action="searchResult.jsp"
			method="get">
			<input class="form-control form-control-mint w-100" type="text"
				name="search" placeholder="Search" aria-label="Search">
		</form>
		<ul class="navbar-nav px-3">
			<li class="nav-item text-nowrap"><a class="nav-link" href="${pageContext.request.contextPath}/logout"><fmt:message
						key="welcome.signout" /></a></li>
		</ul>
	</nav>

	<div class="container-fluid">
		<div class="row no-gutter">
			<nav class="col-md-2 sidebar">
				<div class="sidebar-sticky">
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="user"></span> <fmt:message
									key="welcome.signedinas" />: <%=session.getAttribute("name")%>
						</a></li>
						<li class="nav-item"><a class="nav-link active" href="#">
								<span data-feather="cast"></span> <fmt:message
									key="welcome.casts" />
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="users"></span> <fmt:message
									key="welcome.following" />
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="globe"></span> <fmt:message key="welcome.popular" />
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/addPodcast.jsp"> <span
								data-feather="plus-square"></span> <fmt:message
									key="welcome.addapodcast" />
						</a></li>
					</ul>
				</div>
			</nav>
		</div>
	</div>

<div role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
	<h5 class="my-0 mr-md-auto font-weight-normal">Results for "${searchEntry}"</h5>
		<c:forEach var="podresult" items="${search}">
			<div class="row">

				<div class="card flex-md-row mb-4 box-shadow h-md-250"
					style="max-width: 70%; border-radius: 10px; margin-left: auto; margin-right: auto;">
					<div class="card-body d-flex flex-column align-items-start">
						<h3 class="mb-0">
							<a class="text-dark" href="${podresult.url}">${podresult.name}</a>
						</h3>
						<div class="mb-1 text-muted">Nov 12</div>
						<p class="card-text mb-auto">${podresult.description}</p>
					</div>
					<img class="card-img-right flex-auto d-none d-md-block"
						data-src="${podresult.thumbnail_url}" alt="Thumbnail [200x250]"
						style="width: 200px; height: 250px; border-radius: 0px 10px 10px 0px;margins:0px"
						src="${podresult.thumbnail_url}" data-holder-rendered="true">
				</div>

			</div>
		</c:forEach>

		<div class="droppod-player">
			<div class="droppod-player-controls">
				<button class="droppod-play">
					<i class="fa fa-play"></i><span>Play</span>
				</button>
				<button class="droppod-pause">
					<i class="fa fa-pause"></i><span>Pause</span>
				</button>
				<button class="droppod-rewind">
					<i class="fa fa-fast-backward"></i><span>Rewind</span>
				</button>
				<button class="droppod-fast-forward">
					<i class="fa fa-fast-forward"></i><span>FastForward</span>
				</button>
				<span class="droppod-currenttime droppod-time">00:00</span>
				<progress class="droppod-progress" value="0"></progress>
				<span class="droppod-duration droppod-time">00:00</span>
				<button class="droppod-speed">1x</button>
				<button class="droppod-mute">
					<i class="fa fa-volume-up"></i><span>Mute/Unmute</span>
				</button>
			</div>
			<audio id="droppod-audio"
				src="http://traffic.megaphone.fm/GLT3407800731.mp3"></audio>
			<a class="droppod-download"
				href="http://traffic.megaphone.fm/GLT3407800731.mp3" download>Download
				MP3</a>
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
</fmt:bundle>
</html>