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
			
			<h1>Users Report</h1>
			<table border="1">
				<tr align="center">
					<th width="120">Number of Users</th>
					<th width="120">Max Marks</th>
					
				</tr>
				
					<tr align="center">
						<td>${usersreport.numberOfUsers}</td>
						<td>${usersreport.maxMarks}</td>						
					</tr>				
			</table>

	</div>
</body>
</html>