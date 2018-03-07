<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<sql:query var="rs" dataSource="jdbc/droppod">
select id, name, description, thumbnail_url from droppod.podcasts
</sql:query>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
	
	<c:set var="searchItem"
	value="${search}"
	scope="session" />
<c:set var="myVar" value="${param.search}" />

<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">

	<html lang="${language}">
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="welcome" /> "${language}"</title>
	<link rel='stylesheet prefetch'
		href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h4>Results for  "${param.search}"</h4>


	<c:forEach var="row" items="${rs.rows}">
	<c:if test = "${fn:contains(row.name, myVar)}">

    Name ${row.name}<br />
    Description ${row.description}<br />
		<div class="album-art">
			<img src="${row.thumbnail_url}">
		</div>
		</c:if>
	
	</c:forEach>
	<button id="thumb"><span>THIS</span>
			</button>
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
		<audio id="droppod-audio" src="http://traffic.megaphone.fm/GLT3407800731.mp3"></audio>
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