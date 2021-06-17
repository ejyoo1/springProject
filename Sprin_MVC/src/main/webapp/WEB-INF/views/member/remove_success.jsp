<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<script>
   alert("${member.name}님의 정보가 삭제되었습니다.");
   
   <c:if test="${empty loginUser}">
      window.opener.parent.location.href = "<%=request.getContextPath()%>";
   </c:if>
   
   <c:if test="${!empty loginUser}">
      window.opener.parent.location.reload();
   </c:if>
      
//    window.onload=function(){
//     CloseWindow();
//    }
		window.close();
</script>