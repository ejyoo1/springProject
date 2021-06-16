<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>Yoo's HOME</title>
<head>
	<style>
		*{margin:0;padding:0;}
	</style>
</head>

<body>
		
		<%@ include file="/WEB-INF/views/include/main_header.jsp" %>
		
		<%@ include file="/WEB-INF/views/include/main_asside.jsp" %>
		
		<div class="content-wrapper">
			<iframe name="ifr" src="/main.do" frameborder="0" style="width:100%;height:85vh;"></iframe>
		</div> <!-- ./content-wrapper -->
		<%@ include file="/WEB-INF/views/include/main_footer.jsp" %>
	
	<!-- handlebars -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"></script>
	<!-- page script -->
	<script src="/resources/js/index.js"></script>
	<script type="text/x-handlebars-template"  id="subMenu-list-template" >
	{{#each .}}
		<li class="nav-item subMenu" >
      	<a href="javascript:goPage('{{murl}}','{{mcode}}');initPageParam();" class="nav-link">
          <i class="{{micon}}"></i>
             <p>{{mname}}</p>
        </a>
		</li>
	{{/each}}
	</script>
	<script>
	function init(){ // 문서가 다 로드된 뒤에 읽을 수 있도록 설정(JQuery 인식 못하는 문제 해결) ==> JQuery는 문서가 다 읽어진 후에 적용됨 ( 작업최소화해야 함. 페이지 퍼포먼스 떨어짐. )
		goPage('${menu.murl}','${menu.mcode}'); // 페이지 유지
		subMenu('${menu.mcode}'.substring(0,3)+"0000");
	}
	</script>
</body>
