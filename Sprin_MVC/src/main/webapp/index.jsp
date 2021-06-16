<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty loginUser}">
	<script>
		location.href="index.do";
	</script>
</c:if>
<c:if test="${empty loginUser}">
		<jsp:forward page="/WEB-INF/views/common/login.jsp"/><!-- request.foward -->
</c:if>


<!-- sidemesh url 패턴떄문에 loginForm.do 를 유지해야하므로 아래 두가지 방법은 사용할 수 없다. -->
<%-- <%@ include file="/WEB-INF/views/common/loginForm.jsp" %><!-- url 전환 안됨  forward--> --%>
<%-- <jsp:include page="/WEB-INF/views/common/loginForm.jsp"></jsp:include> --%><!-- url 전환 안됨 forward-->


<!--  url 전환 됨(redirect) -->
<!-- <script> -->
<!-- 	location.href="loginForm.do"; -->
<!-- </script> -->
