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
<td><a href="${pageContext.request.contextPath}/userForm">User Form | </a></td>
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
<h1>Branch Form</h1>

<f:form action="saveBranch" modelAttribute="branch">
<table border="1">

<tr>
<td>Branch Id: </td>
<td>
<f:input path="branchId" value="${nextBranchId}" readonly="true"/>
</td>
<td><f:errors path="branchId"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Branch Name: </td>
<td>
<f:input path="branchName" value="${b.getBranchName()}" />
</td>
<td><f:errors path="branchName"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td colspan="3" align="center"><strong>Address</strong></td>
</tr>

<tr>
<td>Address Line1</td>
<td>
<f:input type="text" path="branchAddress.addressLine1" value="${b.getBranchAddress().getAddressLine1()}"/>
</td>
<td><f:errors path="branchAddress.addressLine1"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Address Line2</td>
<td>
<f:input type="text" path="branchAddress.addressLine2" value="${b.getBranchAddress().getAddressLine2()}"/>
</td>
<td><f:errors path="branchAddress.addressLine2"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>City</td>
<td>
<f:input type="text" path="branchAddress.city" value="${b.getBranchAddress().getCity()}"/>
</td>
<td><f:errors path="branchAddress.city"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>State</td>
<td>
<f:input type="text" path="branchAddress.state" value="${b.getBranchAddress().getState()}"/>
</td>
<td><f:errors path="branchAddress.state"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Country</td>
<td>
<f:input type="text" path="branchAddress.country" value="${b.getBranchAddress().getCountry()}"/>
</td>
<td><f:errors path="branchAddress.country"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>ZIP</td>
<td>
<f:input type="text" path="branchAddress.zip" value="${b.getBranchAddress().getZip()}"/>
</td>
<td><f:errors path="branchAddress.zip"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td colspan="3" align="center" ><input type="submit"  value="Submit"/></td>
</tr>

</table>
</f:form>

<p/>
<h1>List of Branches</h1>

<table border="1">
<tr>
<th><a href="findAllBranches?sortBy=branchId">Branch Id</a></th> 
<th><a href="findAllBranches?sortBy=branchName">Branch Name</a></th> 
<th>Address</th><th>Actions</th>
</tr>

<tr>
<c:forEach items="${branches}" var="branch">
<td>${branch.getBranchId()}</td>
<td>${branch.getBranchName()}</td>
<td>${branch.getBranchAddress().getCity()}, ${branch.getBranchAddress().getState()}</td>
<td>
<a href="deleteBranch?branchId=${branch.getBranchId()}">Delete</a>
<a href="updateBranch?branchId=${branch.getBranchId()}">Update</a>
</td>
</tr>

</c:forEach>

</table>

</div>

</body>
</html>