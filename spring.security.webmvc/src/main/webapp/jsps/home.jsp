<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
	<p><b>${text}</b></p>
	<a href="${pageContext.request.contextPath}/CTC" >Click for CTC Time</a> <br/>
	<a href="${pageContext.request.contextPath}/IST" >Click for IST Time</a> <br/>
	<form:form action="${pageContext.request.contextPath}/customlogout" method="POST">
	<input type="submit" value="Logout">
</form:form>
</body>
</html>