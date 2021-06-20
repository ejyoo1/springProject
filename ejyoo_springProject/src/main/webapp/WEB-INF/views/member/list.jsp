<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="cri" value="${pageMaker.cri}" />
<!-- 마크업을 템플릿화 하였으므로 구분자만 삽입. -->
<head></head>

<title>회원 목록</title>

<body>

	<!-- Main content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row md-2">
				<div class="col-sm-6">
					<h1>회원목록</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="list.do"> <i
								class="fa fa-dashboard"></i>회원관리
						</a></li>
						<li class="breadcrumb-item active">목록</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<!-- Main Content -->
	<section class="content">
		<div class="card">
			<div class="card-header with-border">
				<button type="button" class="btn btn-primary" onclick="OpenWindow('registForm.do','회원등록',800,700)">회원등록</button>
				<div id="keyword" class="card-tools" style="width: 550px;">
					<div class="input-group row">
						<!-- jquery 라이브러리 위에 function 이 오기때문에 script에 jquery를 사용하여 function을 작성하여도 메서드 찾을 수 없음. -->
						<!-- function call을 사용해야 함. -->
						<select class="form-control col-md-3" name="perPageNum" id="perPageNum"
									 	onchange="list_go();">
							<option value="10">정렬개수</option>
							<option value="2" ${cri.perPageNum == 2 ? 'selected' : ''}>2개씩</option>
							<option value="3" ${cri.perPageNum == 3 ? 'selected' : ''}>3개씩</option>
							<option value="5" ${cri.perPageNum == 5 ? 'selected' : ''}>5개씩</option>
							
						</select> 
						<select class="form-control col-md-3" name="searchType" id="searchType">
							<option value="" ${cri.searchType eq '' ? 'selected' : ''}>검색구분</option>
							<option value="i" ${cri.searchType eq 'i' ? 'selected' : ''}>아이디</option>
							<option value="p" ${cri.searchType eq 'p' ? 'selected' : ''}>전화번호</option>
							<option value="e" ${cri.searchType eq 'e' ? 'selected' : ''}>이메일</option>
						</select>
						<input class="form-control" type="text" name="keyword"
									 placeholder="검색어를 입력하세요." value="${cri.keyword}" />
						<span class="input-group-append">
							<button class="btn btn-primary" type="button"
											id="searchBtn" data-card-widget="search" onclick="list_go(1)"><!-- list_go(1) : 1번 페이지 -->
								<i class="fa fa-fw fa-search"></i>
							</button>
						</span>
					</div>
					<!-- ./input-group row -->
				</div>
				<!-- ./card-tools -->
			</div>
			<!-- ./card-header with-border -->
			<div class="card-body" style="text-align: center;">
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-boardered">
							<thead>
								<tr>
									<th>아이디</th>
									<th>패스워드</th>
									<th>이메일</th>
									<th>전화번호</th>
									<th>등록날짜</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${memberList}" var="member">
									<tr onclick="OpenWindow('detail.do?id=${member.id}','','800','900');" style="cursor:pointer;">
										<td>${member.id}</td>
										<td>${member.pwd}</td>
										<td>${member.email}</td>
										<td>${member.phone}</td>
										<td><fmt:formatDate value="${member.regdate }"
												pattern="yyyy-MM-dd" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div><!-- ./col-sm-12 -->
				</div><!-- ./row -->
			</div><!-- ./card-body -->
			<div class="card-footer">
				<c:set var="list_url" value="list.do"></c:set>
				<%@ include file="/WEB-INF/views/common/pagingtion.jsp" %>
			</div><!-- ./card-footer -->
	</div>
</section>

<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
</body><!-- 구분자 역할 -->