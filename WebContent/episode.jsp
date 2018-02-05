<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel='stylesheet prefetch'
	href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<c:forEach items="${episodes}" var="episodes">
    <p>
    	<c:out value="${episodes.name}" />
    	<c:out value="${episodes.description}" />
    	<button class="episode-play-button" value="${episodes.url}">PLAY</button>
    	
    </p>
</c:forEach>


<c:forEach var="row" items="${rs.rows}">
		<div class="image" style="width:130px; height:130px;">
			<a href="${pageContext.request.contextPath}/podcastServlet?uuid=${row.uuid}">
			<img src="${row.thumbnail_url}" alt="${row.name}" style="width: 100%; height: 100%; border-radius: 3px;">
			</a>
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
		<audio id="droppod-audio" src=""></audio>
		<a class="droppod-download"
			href="http://traffic.megaphone.fm/GLT3407800731.mp3" download>Download
			MP3</a>
	</div>

	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
	<script src="js/welcome.js"></script>

<c:if test="${not empty error}">Error: ${error}</c:if>
</body>
</html>