<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome <%=session.getAttribute("name")%></title>
<link rel='stylesheet prefetch'
	href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<form action="addPodcastServlet" method="post">
        <fieldset style="width: 300px">
            <legend> Add Podcast </legend>
            <table>
                <tr>
                    <td>Podcast URL</td>
                    <td><input type="text" name="podcasturl" required="required" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Add" /></td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</html>