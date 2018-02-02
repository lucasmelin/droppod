<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
<html lang="${language}">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="welcome"/> <%=session.getAttribute("name")%></title>
<link rel='stylesheet prefetch'
	href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h3>Login successful!!!</h3>
	<h4>
		<fmt:message key="hello"/>,
		<%=session.getAttribute("name")%></h4>
		
		<a href="./addPodcast"><fmt:message key="addpodcast"/></a>

	<div class="album-art">
		<img
			src="http://static.megaphone.fm/podcasts/05f71746-a825-11e5-aeb5-a7a572df575e/image/uploads_2F1481751087092-ufv8g6eti0741gev-3f91a614fc49c85e19454c0c86b1b753_2FSHOW%2BART.png">
	</div>

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
		<audio src="http://traffic.megaphone.fm/GLT3407800731.mp3"></audio>
		<a class="droppod-download"
			href="http://traffic.megaphone.fm/GLT3407800731.mp3" download>Download
			MP3</a>
	</div>
	
	<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
<script src="js/welcome.js"></script>
</body>
</fmt:bundle>
</html>