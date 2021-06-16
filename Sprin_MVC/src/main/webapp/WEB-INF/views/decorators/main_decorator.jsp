<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title><decorator:title default="YOO's System"/></title><!-- decorator:title : 내보내려고 하는 타이틀을 붙임 -->

<%@ include file="/WEB-INF/views/include/style.jsp" %>

<decorator:head />
</head>
<body class="hold-transition sidebar-mini" onload="init();"><!-- onload는 최소화.. sitemesh를 써야하기때문에 어쩔수없이 사용.. window onload function은 한번밖에 사용못함 두번이상이면 작동 안함. -->
	<div class="wrapper">

		<decorator:body /><!-- htmlParser에 의해서 원본에 있던 body를 붙임 -->
	</div>
	<%@ include file="/WEB-INF/views/include/js.jsp" %>
	<script src="/resources/js/common.js"></script>
</body>
</html>