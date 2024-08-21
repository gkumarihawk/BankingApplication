<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    // Function to fill current date and set readonly attribute
    $(document).ready(function() {
        var now = new Date();
        var year = now.getFullYear();
        var month = (now.getMonth() + 1).toString().padStart(2, '0'); // Adding leading zero if needed
        var day = now.getDate().toString().padStart(2, '0'); // Adding leading zero if needed
        var currentDate = year + '-' + month + '-' + day;
        
        // Set value to transaction date input field
        $('#accountDateOpened').val(currentDate);
    });
</script>

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
<td><a href="${pageContext.request.contextPath}/userForm">User Form | </a></td>
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
<h1>Account Form</h1>

<f:form action="saveAccount" modelAttribute="account">
<table border="1">

<tr>
<td>Account Id: </td>
<td>
<f:input path="accountId" value="${nextAccountId}" readonly="true"/>
</td>
<td><f:errors path="accountId"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td class="label">Account Type</td>
<td>
<c:forEach items="${accountTypes}" var="at">
<c:if test="${retrievedAccountType.equals(at.toString())}">
<f:radiobutton path="accountType"  label="${at}" value="${at}" checked="checked" />
</c:if>
<c:if test="${!retrievedAccountType.equals(at.toString())}">
<f:radiobutton path="accountType" label="${at}" value="${at}"/>
</c:if>
</c:forEach>
</td>
<td><f:errors path="accountType"  cssStyle="color:red;"></f:errors></td>
 </tr>
 
 <tr>
<td>Account Number:</td>
<td>
<f:input path="accountNumber" value="${a.getAccountNumber()}"/>
</td>
<td><f:errors path="accountNumber"  cssStyle="color:red;"></f:errors></td>
</tr>
 
 
 
<tr>
<td>Branch Id:</td>
<td>
<f:input path="accountBranch" value="${a.getAccountBranch().getBranchId()}"/>
</td>
<td><f:errors path="accountBranch"  cssStyle="color:red;"></f:errors></td>
</tr>
 
<tr>
<td>Account Holder: </td>
<td>
<f:input path="accountHolder" value="${a.getAccountHolder()}"/>
</td>
<td><f:errors path="accountHolder"  cssStyle="color:red;"></f:errors></td>
</tr>

<%-- <tr>
<td>Account Opened Date: </td>
<td>
<f:input type="date" path="accountDateOpened" value="${a.getAccountDateOpened()}"/>
</td>
<td><f:errors path="accountDateOpened"  cssStyle="color:red;"></f:errors></td>
</tr> --%>

<tr>
    <td>Account Opened Date:</td>
    <td>
        <f:input type="date" path="accountDateOpened" id="accountDateOpened" readonly="true"/>
    </td>
    <td><f:errors path="accountDateOpened" cssStyle="color:red;"></f:errors></td>
</tr>

 
<tr>
<td>Account Balance: </td>
<td>
<f:input path="accountBalance" value="${a.getAccountBalance()}"/>
</td>
<td><f:errors path="accountBalance"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Customer Id:</td>
<td>
<f:input path="accountCustomer" value="${a.getAccountCustomer().getCustomerId()}"/>
</td>
<td><f:errors path="accountCustomer"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td colspan="3" align="center" ><input type="submit"  value="Submit"/></td>
</tr>

</table>
</f:form>

<p/>
<h1>List of Accounts</h1>

<table border="1">
<tr>
<th><a href="findAllAccounts?sortBy=accountId">Account Id</a></th> 
<th>Account Type</th>
<th><a href="findAllAccounts?sortBy=accountNumber">Account Number</a></th> 
<th>Branch Id</th>
<th><a href="findAllAccounts?sortBy=accountHolder">Account Holder</a></th> 
<th><a href="findAllAccounts?sortBy=accountDateOpened">Account Opened Date</a></th> 
<th><a href="findAllAccounts?sortBy=accountBalance">Account Balance</a></th> 
<th>Customer Id</th>
<th>Actions</th>
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

</div>
</body>
</html>
