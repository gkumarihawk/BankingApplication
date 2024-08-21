<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
<div align="center">
<br>Welcome,: <sec:authentication property="principal.username"/>
<br>Your roles are: <sec:authentication property="principal.authorities"/>
<br></br> <a href="userForm">User Form</a>
</div>
</body>
</html>