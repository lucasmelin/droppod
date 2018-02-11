<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
<html lang="${language}">
<head>
<link rel="stylesheet" type="text/css" href="css/StyleSheet.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="welcome.addapodcast" /></title>
</head>
<body>
	<form action="addPodcastServlet" method="post">
        <fieldset style="width: 300px">
            <legend><fmt:message key="welcome.addapodcast" /></legend>
            <table>
                <tr>
                    <td><fmt:message key="addpodcast.podcasturl" /></td>
                    <td><input type="text" name="podcasturl" required="required" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="<fmt:message key="addpodcast.add" />" /></td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</fmt:bundle>
</html>