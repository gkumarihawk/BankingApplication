		<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Role Form</title>
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
<td><a href="${pageContext.request.contextPath}/userForm">User Form | </a></td>
<td><a href="${pageContext.request.contextPath}/home">Home | </a></td>
<td><a href="${pageContext.request.contextPath}/accountForm">Account Form | </a></td>
<td><a href="${pageContext.request.contextPath}/branchForm">Branch Form | </a></td>
<td><a href="${pageContext.request.contextPath}/bankTransactionForm">Transaction Form | </a></td>
<td><a href="${pageContext.request.contextPath}/customerForm">Customer Form | </a></td>
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
<h1>Role Form</h1>

<f:form action="saveRole" modelAttribute="role">
<table border="1">
<tr>
<td>Role Id: </td>
<td>
<f:input path="roleId" value="${r.getRoleId()}" />
</td>
</tr>

<tr>
<td>Role Name: </td>
<td><f:input path="roleName" value="${r.getRoleName()}"/></td>
</tr>



<tr>
<td colspan="2" align="center"><input type="submit" value="submit"/></td>
</tr>
</table>


</f:form>

<p/>
<h1>List of Roles</h1>

<table border="1">
<tr>
<th><a href="findAllRoles?sortBy=roleId">Role id</a></th> 
<th><a href="findAllRoles?sortBy=roleName">Role Name</a></th> 
<th>Action</th>
</tr>

<tr>
<c:forEach items="${roles}" var="role">
<td>${role.getRoleId()}</td>
<td>${role.getRoleName()}</td>

<td>
<a href="deleteRole?roleId=${role.getRoleId()}">Delete</a>

<a href="updateRole?roleId=${role.getRoleId()}">Update</a>
</td>
</tr>

</c:forEach>

</table>

</div>

</body>
</html>