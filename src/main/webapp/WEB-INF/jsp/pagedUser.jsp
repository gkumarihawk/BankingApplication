<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>User Form</title>
<style>
.myCss{
  color:blue;
  font-style:italic;
}
</style>

</head>
<body>
<div  align="center">
<table>
<tr>
<td><a href="pagedUser?pageNo=1&pageSize=5&sortedBy=username">Users | </a> 
<td><a href="pagedRole?pageNo=1&pageSize=1&sortedBy=roleName">Roles | </a>
<td><a href="pagedCustomer?pageNo=1&pageSize=5&sortedBy=customerName">Customers | </a>
<td><a href="pagedBranch?pageNo=1&pageSize=2&sortedBy=branchName">Branches | </a>
<td><a href="pagedAccount?pageNo=1&pageSize=2&sortedBy=accountHolder">Accounts | </a>
<td><a href="pagedBankTransaction?pageNo=1&pageSize=2&sortedBy=bankTransactionDateTime">Transactions | </a>
<td><a href="${pageContext.request.contextPath}/roleForm">Role Form | </a></td>
<td><a href="${pageContext.request.contextPath}/home">Home | </a></td>
<td><a href="${pageContext.request.contextPath}/accountForm">Account Form | </a></td>
<td><a href="${pageContext.request.contextPath}/branchForm">Branch Form | </a></td>
<td><a href="${pageContext.request.contextPath}/bankTransactionForm">Transaction Form | </a></td>
<td><a href="${pageContext.request.contextPath}/customerForm">Customer Form | </a></td>
<td><a href="${pageContext.request.contextPath}/userForm">User Form | </a></td>
<sec:authorize access="isAuthenticated">
<td><a href="${pageContext.request.contextPath}/logout">Logout</a></td>
</sec:authorize>
</tr>
</table>
</div>

<div align="center">
<sec:authorize access="isAuthenticated">
	<br>Welcome <br> <sec:authentication property="principal.username"/>
<br>your authorities are: <sec:authentication property="principal.authorities"/>
</sec:authorize>
<sec:authorize access="!isAuthenticated">
	<br> <a href="login">login</a>
</sec:authorize>

<c:if test="${not empty users}">
<h1>List of Users</h1>
<table border="1">
<tr>
<th>User id</th><th>Name</th><th>Password</th><th>Email</th><th>Roles</th><th>Action</th>
</tr>

<tr>
<c:forEach items="${users}" var="user">
<td>${user.getUserId()}</td>
<td>${user.getUsername()}</td>
<td>${user.getPassword()}</td>
<td>${user.getEmail()}</td>
<td>
<c:forEach items="${user.getRoles()}" var="role">
${role.getRoleName()}
</c:forEach>
</td>
<td>
<a href="deleteUser?userId=${user.getUserId()}">Delete</a>
<a href="updateUser?userId=${user.getUserId()}">Update</a>
</td>
</tr>
</c:forEach>
</table>
<p/>
<p/>
<p>Total Pages: ${totalPages} </p>

<c:set var="totalPages" value="${totalPages}"></c:set>
<c:set var="sortedBy" value="${sortedBy}"></c:set>
<c:set var="pageSize" value="${pageSize}"></c:set>

<%
for(int i=0; i< (int)pageContext.getAttribute("totalPages"); i++){
	out.println("<a href=\"pagedUser?pageNo="+i
	+"&pageSize="+request.getParameter("pageSize")
	+"&sortedBy="+request.getParameter("sortedBy")
	+"\">"
	+i
	+"</a>");
}
%>
</c:if>
</div>
</body>
</html>