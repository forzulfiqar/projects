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
			

			<br />
			<br />


			<h1>Contact List</h1>
			<table border="1">
				<tr align="center">
					<th width="120">Region Name</th>
					<th width="120">Countries</th>
					

				</tr>


				<c:forEach var="regionObj" items="${regionsList}" varStatus="status">
					<tr align="center">
						<td>${regionObj.name}</td>
						<td> 
							<c:forEach var="countryObj" items="${regionObj.countries}" varStatus="statusCountry">
								${countryObj.name}, 
							</c:forEach>
						</td>
					</tr>

				</c:forEach>
			</table>

		</form:form>

	</div>
</body>
</html>