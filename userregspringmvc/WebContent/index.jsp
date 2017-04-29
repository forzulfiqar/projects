<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Title : Index.jsp</h1>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<!-- For login user -->
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
	</c:if>
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
		
			<h2>
				 <a href="users/listusers"> List All users </a>
			</h2>
			<h2>
				User : ${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()"> Logout</a>
			</h2>
		</c:if>
		
		<c:if test="${pageContext.request.userPrincipal.name == null}">
			
			<h2>
				<a href="users/login"> Login</a>
			</h2>
		</c:if>
		
</body>
</html>