<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bank Transaction Form</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    // Function to fill current date and time
    function fillCurrentDateTime() {
        var now = new Date();
        var year = now.getFullYear();
        var month = (now.getMonth() + 1).toString().padStart(2, '0'); // Adding leading zero if needed
        var day = now.getDate().toString().padStart(2, '0'); // Adding leading zero if needed
        var hours = now.getHours().toString().padStart(2, '0'); // Adding leading zero if needed
        var minutes = now.getMinutes().toString().padStart(2, '0'); // Adding leading zero if needed
        var dateTimeString = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
        $('#bankTransactionDateTime').val(dateTimeString); // Set value to transaction date input field
    }

    // Call the function to fill current date and time when the page loads
    fillCurrentDateTime();
});
</script>


<script>
    function showFields(transactionType) {
        var depositFields = document.getElementById("depositFields");
        var withdrawFields = document.getElementById("withdrawFields");
        var transferFields = document.getElementById("transferFields");
        if (transactionType === 'DEPOSIT') {
            depositFields.style.display = 'table-row';
            withdrawFields.style.display = 'none';
            transferFields.style.display = 'none'; 
        } else if (transactionType === 'WITHDRAW') {
            depositFields.style.display = 'none';
            withdrawFields.style.display = 'table-row';
            transferFields.style.display = 'none'; 
        } else if (transactionType === 'TRANSFER') {
            depositFields.style.display = 'table-row';
            withdrawFields.style.display = 'table-row';
            transferFields.style.display = 'none';
        } else {
            depositFields.style.display = 'none';
            withdrawFields.style.display = 'none';
            transferFields.style.display = 'none';
        }
    }
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
<td><a href="${pageContext.request.contextPath}/accountForm">Account Form | </a></td>
<td><a href="${pageContext.request.contextPath}/branchForm">Branch Form | </a></td>
<td><a href="${pageContext.request.contextPath}/userForm">User Form | </a></td>
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
<h1>Bank Transaction Form</h1>


<f:form action="saveBankTransaction" modelAttribute="bankTransaction">
<table border="1">

<tr>
<td>Bank Transaction Id:</td>
<td>
<f:input path="bankTransactionId" value="${nextBankTransactionId}" readonly="true"/>
</td>
<td><f:errors path="bankTransactionId" cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Transaction Amount:</td>
<td>
<f:input path="bankTransactionAmount"/>
</td>
<td><f:errors path="bankTransactionAmount" cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Transaction Type:</td>
<td>
<f:select path="bankTransactionType" onchange="showFields(this.value)">
    <f:option value="">--Select--</f:option>
    <c:forEach items="${bankTransactionTypes}" var="b">
        <f:option value="${b}">${b}</f:option>
    </c:forEach>
</f:select>
</td>
<td><f:errors path="bankTransactionType" cssStyle="color:red;"></f:errors></td>
</tr>

<tr id="depositFields" style="display: none;">
<td>To Account Id:</td>
<td>
<f:input path="bankTransactionToAccount"/>
</td>
<td><f:errors path="bankTransactionToAccount" cssStyle="color:red;"></f:errors></td>
</tr>

<tr id="withdrawFields" style="display: none;">
<td>From Account Id:</td>
<td>
<f:input path="bankTransactionFromAccount"/>
</td>
<td><f:errors path="bankTransactionFromAccount" cssStyle="color:red;"></f:errors></td>
</tr>

<%-- <tr>
<td>Transaction Date:</td>
<td>
<f:input type="datetime-local" path="bankTransactionDateTime"/>
</td>
<td><f:errors path="bankTransactionDateTime" cssStyle="color:red;"></f:errors></td>
</tr> --%>

<tr>
    <td>Transaction Date:</td>
    <td>
        <f:input type="datetime-local" path="bankTransactionDateTime" id="bankTransactionDateTime" readonly="true"/>
    </td>
    <td><f:errors path="bankTransactionDateTime" cssStyle="color:red;"></f:errors></td>
</tr>


<tr>
<td>Comments:</td>
<td>
<f:input path="comments"/>
</td>
<td><f:errors path="comments" cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td colspan="3" align="center"><input type="submit" value="Submit"/></td>
</tr>

</table>
</f:form>

<h1>List of Transactions</h1>

<!-- Date Filter Form -->
    <form action="filterTransactions" method="get">
        <label for="startDate">Start Date:</label>
        <input type="datetime-local" id="startDate" name="startDate">
        <label for="endDate">End Date:</label>
        <input type="datetime-local" id="endDate" name="endDate">
        <input type="submit" value="Filter">
    </form>

<table border="1">
<tr>

<th><a href="findAllBankTransactions?sortBy=bankTransactionId">Id</a></th> 
<th><a href="findAllBankTransactions?sortBy=bankTransactionAmount">Amount</a></th>
<th>Transaction Type</th>
<th>From Account Number</th>
<th>To Account Number</th>
<th><a href="findAllBankTransactions?sortBy=bankTransactionDateTime">Transaction Date</a></th>
<th>Comments</th>
<!-- <th>Actions</th> -->
</tr>

<c:forEach items="${bankTransactions}" var="bankTransaction">
<tr>
<td>${bankTransaction.bankTransactionId}</td>
<td>${bankTransaction.bankTransactionAmount}</td>
<td>${bankTransaction.bankTransactionType}</td>
<td>${bankTransaction.bankTransactionFromAccount.accountId}</td>
<td>${bankTransaction.bankTransactionToAccount.accountId}</td>
<td>${bankTransaction.bankTransactionDateTime}</td>
<td>${bankTransaction.comments}</td>
<%-- <td>
<a href="deleteBankTransaction?bankTransactionId=${bankTransaction.bankTransactionId}">Delete | </a>
<a href="updateBankTransaction?bankTransactionId=${bankTransaction.bankTransactionId}">Update</a>
</td> --%>
</tr>
</c:forEach>

</table>

</div>
</body>
</html>
