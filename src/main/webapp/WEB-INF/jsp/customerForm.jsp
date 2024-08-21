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
<td><a href="${pageContext.request.contextPath}/branchForm">Branch Form | </a></td>
<td><a href="${pageContext.request.contextPath}/bankTransactionForm">Transaction Form | </a></td>
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
<h1>Customer Form</h1>

<f:form action="saveCustomer" modelAttribute="customer">
<table border="1">


<tr>
<td>Customer Id: </td>
<td>
<f:input path="customerId" value="${nextCustomerId}" readonly="true"/>
<td><f:errors path="customerId"  cssStyle="color:red;"></f:errors></td>
</td>
</tr>

<tr>
<td>Customer Name: </td>
<td>
<f:input path="customerName" value="${c.getCustomerName()}"/>
<td><f:errors path="customerName"  cssStyle="color:red;"></f:errors></td>
</td>
</tr>


<tr>
<td>Gender</td>
<td>
<c:forEach items="${genders}" var="g">
<c:if test="${retrievedGender.equals(g.toString())}">
<f:radiobutton path="customerGender"  label="${g}" value="${g}" checked="checked" />
</c:if>
<c:if test="${!retrievedGender.equals(g.toString())}">
<f:radiobutton path="customerGender" label="${g}" value="${g}"/>
 </c:if>
</c:forEach>
</td>
<td><f:errors path="customerGender"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Customer DOB: </td>
<td>
<f:input type="date" path="customerDOB" value="${c.getCustomerDOB()}"/>
<td><f:errors path="customerDOB"  cssStyle="color:red;"></f:errors></td>
</td>
</tr>

<tr>
<td>Customer Mobile: </td>
<td>
<f:input path="customerMobileNo" value="${c.getCustomerMobileNo()}"/>
<td><f:errors path="customerMobileNo"  cssStyle="color:red;"></f:errors></td>
</td>
</tr>

<tr>
<td colspan="3" align="center"><strong>Address</strong></td>
</tr>

<tr>
<td>Address Line1</td>
<td>
<f:input type="text" path="customerAddress.addressLine1" value="${c.getCustomerAddress().getAddressLine1()}"/>
</td>
<td><f:errors path="customerAddress.addressLine1"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Address Line2</td>
<td>
<f:input type="text" path="customerAddress.addressLine2" value="${c.getCustomerAddress().getAddressLine2()}"/>
</td>
<td><f:errors path="customerAddress.addressLine2"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>City</td>
<td>
<f:input type="text" path="customerAddress.city" value="${c.getCustomerAddress().getCity()}"/>
</td>
<td><f:errors path="customerAddress.city"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>State</td>
<td>
<f:input type="text" path="customerAddress.state" value="${c.getCustomerAddress().getState()}"/>
</td>
<td><f:errors path="customerAddress.state"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Country</td>
<td>
<f:input type="text" path="customerAddress.country" value="${c.getCustomerAddress().getCountry()}"/>
</td>
<td><f:errors path="customerAddress.country"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>ZIP</td>
<td>
<f:input type="text" path="customerAddress.zip" value="${c.getCustomerAddress().getZip()}"/>
</td>
<td><f:errors path="customerAddress.zip"  cssStyle="color:red;"></f:errors></td>
</tr>

<tr>
<td>Customer SSN: </td>
<td>
<f:input path="customerSSN" value="${c.getCustomerSSN()}"/>
</td>
<td><f:errors path="customerSSN"  cssStyle="color:red;"></f:errors></td>

</tr>

<tr>
<td>User Id:</td>
<td>
<f:input path="user" value="${c.getUser().getUserId()}"/>
</td>
<td><f:errors path="user"  cssStyle="color:red;"></f:errors></td>

</tr>

<tr>
<td colspan="3" align="center" ><input type="submit"  value="Submit"/></td>
</tr>


</table>
</f:form>

<p/>
<h1>List of Customers</h1>

<table border="1">
<tr>

<th><a href="findAllCustomers?sortBy=customerId">Customer Id</a></th> 
<th><a href="findAllCustomers?sortBy=customerName">Customer Name</a></th> 
<th><a href="findAllCustomers?sortBy=customerMobileNo">Mobile Number</a></th> 
<th>Gender</th>
<th><a href="findAllCustomers?sortBy=customerDOB">Date Of Birth</a></th> 
<th>Address</th><th>SSN</th>
<th>User Id</th>
<th>Actions</th>
</tr>

<tr>
<c:forEach items="${customers}" var="customer">
<td>${customer.getCustomerId()}</td>
<td>${customer.getCustomerName()}</td>
<td>${customer.getCustomerGender()}</td>
<td>${customer.getCustomerDOB()}</td>
<td>${customer.getCustomerMobileNo()}</td>
<td>${customer.getCustomerAddress().getCity()}, ${customer.getCustomerAddress().getState()}</td>
<td>${customer.getCustomerSSN()}</td>
<td>${customer.getUser().getUserId()}</td>
<td>
<a href="deleteCustomer?customerId=${customer.getCustomerId()}">Delete</a>

<a href="updateCustomer?customerId=${customer.getCustomerId()}">Update</a>
</td>
</tr>

</c:forEach>

</table>

</div>

</body>
</html>