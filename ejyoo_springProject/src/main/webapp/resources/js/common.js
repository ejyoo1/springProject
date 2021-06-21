/**
 * 각 페이지에서 사용할 공통페이지
 * (function call이고 list.jsp에서만 사용할 것이므로 list_go function에 element를 파라미터로 받지 않음.
 */

//function call이기 때문에 javascript이므로 문서가ㅓ 모두 로드된 후에 호출된다. (다 로드된 후에 이벤트 성으로 호출)
// jquery는 function call한 이후에 호출하면 된다.
//function call 없이 jquery를 호출하면 body가 로드되기 전에 호출되는 특성으로 인하여 jquery를 인식하지 못한다.
// 결합도를 낮추기 위해 외부에서 url을 받아서 처리한다.

// pagination list up 함수
// page : page 번호, url : list url
// copyright 유은지 2021.06.01 ing~~~~
function list_go(page, url) {// 페이지 상태 유지 부분
	//alert("page : " + page);
	if(!url) url = "list.do"; // url이 없는 경우 처리 ==> '!url' : url이 없으면(사전 방지)
	
	var jobForm = $('#jobForm');
	
	jobForm.find("[name='page']").val(page);
	jobForm.find("[name='perPageNum']").val($('select[name="perPageNum"]').val());
	jobForm.find("[name='searchType']").val($('select[name="searchType"]').val());
	jobForm.find("[name='keyword']").val($('div.input-group>input[name="keyword"]').val());

	$('form#jobForm').attr({ // 페이지 번호가 없으면 페이지 번호는 1
		action : url,
		method : 'get'
	}).submit();
}

// 팝업창들 띄우기
// 새로운 window 창을 open할 경우 사용되는 함수 (arg : 주소, 창타이틀, 넓이, 길이)
// 새창은 주소줄 못없앰
function OpenWindow(UrlStr, WinTitle, WinWidth, WinHeight){
	winleft = (screen.width - WinWidth) / 2;
	wintop = (screen.height - WinHeight) / 2;
	var win = window.open(UrlStr, WinTitle, "scrollbars=yes,width="+WinWidth+"," // 스크롤바
			+ "height=" + WinHeight + ", top=" + wintop + ", left="
			+ winleft + ", resizable=yes, status=yes"); // 실제로 요청
	win.focus();
}

//// 팝업창 닫기
//function CloseWindow(){
//	window.opener.location.reload(true);
//	window.close();
//}

// 팝업창 닫고 부모창 이동
function CloseWindow(parentURL){
	if(parentURL){
		window.opener.parent.location.href = parentURL;
	} else {
		window.opener.parent.location.reload(true);
	}
	window.close();
}

// 사용자 사진 미리보기 (서버 호출)
function MemberPictureThumb(targetObj, fileName){ // (대상, 이미지파일명)
	alert(fileName+"fileName");
	targetObj.style.backgroundImage = "url('getPicture.do?picture="+ fileName +"')";// 상대경로 || 절대경로 : member/getPicture.do
	targetObj.style.backgroundPosition = "center";
	targetObj.style.backgroundRepeat = "no-repeat";
	targetObj.style.backgroundSize = "cover"; // cover : 꽉차지만 잘림 , contain : 딱맞지만 여백생성 // cover인 경우 backgroundPosition = "center" 를 해야 가운데 기준으로 잘림. // 때에 따라 top left|right / bottom left|right 사용 가능
}