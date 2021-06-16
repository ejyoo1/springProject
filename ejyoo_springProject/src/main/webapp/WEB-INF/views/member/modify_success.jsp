<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
<script>
	alert("${member.name}님의 정보가 수정되었습니다.");
	// 뒤로가기 하면 안됨 : 수정되기 전 화면이 나옴.
	location.href='detail.do?id=${member.id}';
</script>
</body>