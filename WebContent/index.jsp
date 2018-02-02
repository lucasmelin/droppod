<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
<html lang="${language}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Application</title>
</head>
<body>
    <form action="loginServlet" method="post">
        <fieldset style="width: 300px">
            <legend> Login to App </legend>
            <table>
                <tr>
                    <td><fmt:message key="userid"/></td>
                    <td><input type="text" name="username" required="required" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="password"/></td>
                    <td><input type="password" name="userpass" required="required" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="<fmt:message key="login"/>" /></td>
                </tr>
            </table>
        </fieldset>
    </form>
    <form>
    <select id="language" name="language" style="background-color: #003399; color: #f2f2f2" onchange="submit()">
      <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
      <option value="fr" ${language == 'fr' ? 'selected' : ''}>Francais</option>
    </select>
</form>
    
    
</body>
</fmt:bundle>
</html>
