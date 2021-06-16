<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	
	alert("정지되었습니다.");
	<c:if test="${empty loginUser}">//로그인 한 사람
		window.opener.parent.location.href="/";
		window.close();
	</c:if>
	<c:if test="${!empty loginUser}"> // 현재 url만 변경하면 됨.
		location.href="detail.do?id=${member.id}";
	</c:if>
	
	CloseWindow();
	
	
</script>
