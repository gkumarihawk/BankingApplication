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
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 80%;
        margin: 0 auto;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
    }
    .navbar {
        display: flex;
        justify-content: center;
        background-color: #343a40;
        padding: 10px;
        border-radius: 8px;
        margin-bottom: 20px;
    }
    .navbar a {
        color: #fff;
        text-decoration: none;
        margin: 0 10px;
    }
    .navbar a:hover {
        text-decoration: underline;
    }
    h1 {
        color: #343a40;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    table, th, td {
        border: 1px solid #dee2e6;
    }
    th, td {
        padding: 12px;
        text-align: left;
    }
    th {
        background-color: #343a40;
        color: #fff;
    }
    tr:nth-child(even) {
        background-color: #f2f2f2;
    }
    .form-table td {
        padding: 8px;
    }
    .form-table input[type="submit"] {
        background-color: #343a40;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    .form-table input[type="submit"]:hover {
        background-color: #495057;
    }
    .error {
        color: red;
    }
</style>
</head>
<body>
<div class="container">
<div class="navbar">
    <sec:authorize access="isAuthenticated">
        <a href="pagedUser?pageNo=1&pageSize=5&sortedBy=username">Users</a>
        <a href="pagedRole?pageNo=1&pageSize=1&sortedBy=roleName">Roles</a>
        <a href="pagedCustomer?pageNo=1&pageSize=5&sortedBy=customerName">Customers</a>
        <a href="pagedBranch?pageNo=1&pageSize=2&sortedBy=branchName">Branches</a>
        <a href="pagedAccount?pageNo=1&pageSize=2&sortedBy=accountHolder">Accounts</a>
        <a href="pagedBankTransaction?pageNo=1&pageSize=2&sortedBy=bankTransactionDateTime">Transactions</a>
        <a href="${pageContext.request.contextPath}/roleForm">Role Form</a>
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/accountForm">Account Form</a>
        <a href="${pageContext.request.contextPath}/branchForm">Branch Form</a>
        <a href="${pageContext.request.contextPath}/bankTransactionForm">Transaction Form</a>
        <a href="${pageContext.request.contextPath}/customerForm">Customer Form</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </sec:authorize>
</div>

<div align="center">
    <sec:authorize access="isAuthenticated">
        <br>Welcome <br> <sec:authentication property="principal.username"/>
        <br>your authorities are: <sec:authentication property="principal.authorities"/>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated">
        <br> <a href="login">login</a>
    </sec:authorize>
    <h1>User Form</h1>

    <f:form action="saveUser" modelAttribute="user">
    <table class="form-table">
        <tr>
            <td>User Id:</td>
            <td>
                <f:input path="userId" value="${u.getUserId()}"/>
                <f:errors path="userId" cssClass="error"></f:errors>
            </td>
        </tr>
        <tr>
            <td>Name:</td>
            <td>
                <f:input path="username" value="${u.getUsername()}"/>
                <f:errors path="username" cssClass="error"></f:errors>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <f:input path="password" value="${u.getPassword() }"/>
                <f:errors path="password" cssClass="error"></f:errors>
            </td>
        </tr>
        <tr>
            <td>Email:</td>
            <td>
                <f:input path="email" value="${u.getEmail()}"/>
                <f:errors path="email" cssClass="error"></f:errors>
            </td>
        </tr>
        <sec:authorize access="isAuthenticated">
            <tr>
		<td>Roles:</td>
<td>
<c:forEach items="${roles }" var="role">
<c:if test="${retriedRoles.contains(role)}">
<f:checkbox path="roles" label="${role.getRoleName() }" value="${role.getRoleId() }" checked="true"/>
</c:if>

<c:if test="${! retriedRoles.contains(role)}">
<f:checkbox path="roles" label="${role.getRoleName() }" value="${role.getRoleId() }"/>
</c:if>
</c:forEach>

</td>
</tr>
        </sec:authorize>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="submit"/></td>
        </tr>
    </table>
    </f:form>
    
    <sec:authorize access="hasAuthority('Admin')">
        <p/>
        <h1>List of Users</h1>
        <table>
            <tr>
                <th><a href="findAllUsers?sortBy=userId">User id</a></th> 
                <th><a href="findAllUsers?sortBy=username">Name</a></th> 
                <th><a href="findAllUsers?sortBy=password">Password</a></th> 
                <th><a href="findAllUsers?sortBy=email">Email</a></th> 
                <th>Roles</th> 
                <th>Action</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.getUserId()}</td>
                    <td>${user.getUsername()}</td>
                    <td>${user.getPassword()}</td>
                    <td>${user.getEmail()}</td>
                    <td>
                        <c:forEach items="${user.getRoles()}" var="role">
                            ${role.getRoleName()}
                        </c:forEach>
                    </td>
                    <sec:authorize access="hasAuthority('Admin')">
                        <td>
                            <a href="deleteUser?userId=${user.getUserId()}">Delete</a>
                            <a href="updateUser?userId=${user.getUserId()}">Update</a>
                        </td>
                    </sec:authorize>
                </tr>
            </c:forEach>
        </table>
    </sec:authorize>
</div>
</div>
</body>
</html>
