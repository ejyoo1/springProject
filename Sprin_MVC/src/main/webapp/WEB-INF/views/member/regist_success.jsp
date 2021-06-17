<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!-- sitemeth는 태그가 없으면 무조건 body에 넣음. -->

<body>
<script>
	alert("회원등록에 성공했습니다. \n 회원리스트 페이지로 이동합니다.");
	
	// window.opener.parent : 창
	// window.opener : iframe
	// 톰캣 server.xml 에서 URLEncoding="utf-8" 설정 해야 한글 파라미터가 넘어감.
	window.onload=function(){ // ajax를 바로 사용할 수 없기때문에 이와 같은 작업을 함.
		$.ajax({
			url : "<%=request.getContextPath()%>/getMcode.do?mName=회원목록",
			type: "get",
			success:function(menu){
				window.opener.parent.location.href="<%=request.getContextPath()%>/index.do?mCode=" + menu.mcode;
				window.close();
			},
			error : function(error){
				alert("시스템 장애가 발생하였습니다. \n 관리자에게 연락바랍니다.");
				window.opener.history.go(-1);
			}
		});
	}
</script>
</body>