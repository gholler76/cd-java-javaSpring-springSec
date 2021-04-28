<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashbaord Page</title>
</head>
<body>
	<h1>Welcome ${currentUser.firstname}</h1>
   <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    
    <form:form method="POST" action="/" modelAttribute="currentUser">
        <p>
            <form:label path="firstname">First Name: </form:label>
            <form:input path="firstname"/>
        </p>
        <p>
            <form:label path="lastname">Last Name: </form:label>
            <form:input path="lastname"/>
        </p>
        <p>
            <form:label path="username">Email: </form:label>
            <form:input path="username"/>
        </p>
    </form:form>
</body>
</html>
