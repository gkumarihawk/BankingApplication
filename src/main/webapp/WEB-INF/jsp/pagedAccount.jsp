<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Customer Form</title>
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

<h1>List of Accounts</h1>

<table border="1">
<tr>
<th>Id</th><th>Account Type</th><th>Account Number</th><th>Branch Id</th><th>Account Holder</th><th>Account Opened Date</th><th>Account Balance</th><th>Customer Id</th><th>Actions</th>
</tr>

<tr>
<c:forEach items="${accounts}" var="account">
<td>${account.getAccountId()}</td>
<td>${account.getAccountType()}</td>
<td>${account.getAccountNumber()}</td>
<td>${account.getAccountBranch().getBranchId()}</td>
<td>${account.getAccountHolder()}</td>
<td>${account.getAccountDateOpened()}</td>
<td>${account.getAccountBalance()}</td>
<td>${account.getAccountCustomer().getCustomerId()}</td>
<td>
<a href="deleteAccount?accountId=${account.getAccountId()}">Delete | </a>
<a href="updateAccount?accountId=${account.getAccountId()}">Update</a>
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
	out.println("<a href=\"pagedAccount?pageNo="+i
	+"&pageSize="+request.getParameter("pageSize")
	+"&sortedBy="+request.getParameter("sortedBy")
	+"\">"
	+i
	+"</a>");
}
%>



</body>
</html>