<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
</head>
<body>
    <h1>Welcome to the Admin Page ${currentUser.firstname}</h1>
    <h4> <c:out value="${errorMessage}"></c:out></h4>
    <table>
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Email</th>
    			<th>Action</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach var="user" items="${users}">
    		<tr>
    			<td>${user.firstname} ${user.lastname}</td>
    			<td>${user.username}</td>
				<c:forEach var="role" items="${user.roles}">
				<c:if test="${role.id == 2}">
				<td>ADMIN</td>
				</c:if>
				<c:if test="${role.id == 1}">
				<td>
				<form action="/delete" method="post">
				<input type="hidden" name="_method" value="delete">
				<input type="hidden" name="deleteId" value="${user.id}">
				<button type="submit">Delete</button>
				</form>
				<form action="/makeadmin" method="post">
				<input type="hidden" name="_method" value="put">
				<input type="hidden" name="adminId" value="${user.id}">
				<button type="submit">Make Admin</button>
				</form>
				</td>
				</c:if>
	    		</c:forEach >
    		</tr>
    		</c:forEach >
    	</tbody>
    </table>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
</body>
</html>
