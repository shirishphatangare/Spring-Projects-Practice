<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>



<form:form action="${loginProcessingUrl}" method="post">

<c:if test="${param.error != null}">
	Login Error! Please check username and password
</c:if> 

<c:if test="${param.logout != null}">
	You have been logged out
</c:if> 



<p>username: <input type="text" name="username"/> </p>
<p>password: <input type="password" name="password"/></p>
<input type="submit" value="Login">
<input type="reset" value="Reset">
</br>
</br>


</form:form>
</body>
</html>