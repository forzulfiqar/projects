<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users</title>
</head>
<body>
	<div align="center">

		<br />

		<h3>Register New User</h3>
		<form:form method="POST" action="registeruser" modelAttribute="user">
			<table>
				<tr>
					<td><form:label path="firstName">First Name</form:label></td>
					<td><form:input path="firstName" /></td>
				</tr>
				<tr>
					<td><form:label path="lastName">Last Name</form:label></td>
					<td><form:input path="lastName" /></td>
				</tr>
				<tr>
					<td><form:label path="emailAddress">Email Address</form:label></td>
					<td><form:input path="emailAddress" /></td>
				</tr>

				<tr>
					<td><form:label path="phoneNumber">Phone Number</form:label></td>
					<td><form:input path="phoneNumber" /></td>
				</tr>

				<tr>
					<td><form:label path="userName">User Name</form:label></td>
					<td><form:input path="userName" /></td>
				</tr>

				<tr>
					<td><form:label path="password">Password</form:label></td>
					<td><form:input path="password" /></td>
				</tr>

				<tr>
					<td><form:label path="countryId">Country Id</form:label></td>

					<td><form:select path="countryId">
							<c:forEach var="country" items="${countries}">
								<form:option title="${country.name}" value="${country.id}">${country.name}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>


				<tr>
					<td><input type="submit" value="Register User" /></td>
				</tr>
			</table>


			<br />
			<br />


			<h1>Contact List</h1>
			<table border="1">
				<tr align="center">
					<th width="120">User Name</th>
					<th width="120">First Name</th>
					<th width="120">Last Name</th>
					<th width="80">Email Address</th>
					<th width="80">Phone Number</th>
					
				</tr>


				<c:forEach var="userObj" items="${userList}" varStatus="status">
					<tr align="center">
						<td>${userObj.userName}</td>
						<td>${userObj.firstName}</td>
						<td>${userObj.lastName}</td>
						<td>${userObj.emailAddress}</td>
						<td>${userObj.phoneNumber}</td>
						
					</tr>

				</c:forEach>
			</table>

		</form:form>

	</div>
</body>
</html>