<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"></script> <!-- handlebars cdn -->
<script type="text/x-handlebars-template"  id="reply-list-template" >
{{#each .}}
<div class="replyLi" >
   <div class="user-block">
       <img src="<%=request.getContextPath()%>/member/getPictureById/{{replyer}}" class="img-circle img-bordered-sm">
   </div>
    <div class="timeline-item" >
        <span class="time">
          <i class="fa fa-clock"></i>{{prettifyDate regdate}}
          <a class="btn btn-primary btn-xs {{rno}}-a" id="modifyReplyBtn" data-rno={{rno}}
						 onclick="replyModifyModal_go('{{rno}}');"
						 style="display:{{VisibleByLoginCheck replyer}};"
             data-replyer={{replyer}} data-toggle="modal" data-target="#modifyModal">Modify</a>
        </span>
   
        <h3 class="timeline-header"><strong style="display:none;">{{rno}}</strong>{{replyer}}</h3>
        <div class="timeline-body" id="{{rno}}-replytext">{{replytext}} </div>
   </div>
</div>
{{/each}}   
</script>
<script type="text/x-handlebars-template"  id="reply-pagination-template" >
<li class="paginate_button page-item">
	<a href="1" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
		<i class="fas fa-angle-double-left"></i>
	</a>
</li>
<li class="paginate_button page-item">
	<a href="{{#if prev}} {{prevPageNum}} {{/if}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
		<i class="fas fa-angle-left"></i>
	</a>
</li>

{{#each pageNum}}
<li class="paginate_button page-item {{signActive this}}">
	<a href="{{this}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
		{{this}}
	</a>
</li>
{{/each}}

<li class="paginate_button page-item">
	<a href="{{#if next}}{{nextPageNum}}{{/if}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
		<i class="fas fa-angle-right"></i>
	</a>
</li>
<li class="paginate_button page-item">
	<a href="{{realEndPage}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
		<i class="fas fa-angle-double-right"></i>
	</a>
</li>										
</script>
	


<script> //댓글리스트
var replyPage = 1; // 무조건 1번 페이지 보이게 함. (리스트 뿌릴 때 페이지 기준)

window.onload=function(){ // jquery 라이브러리 로드 전에 실행되므로 onload 함수 감싸줌.//boardReplyController
	getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+replyPage);
	
	// 만들어진 li에 이벤트 먹이기(고전방식)
	$('.pagination').on('click','li a', function(event){
		//alert($(this).attr("href"));
		if($(this).attr("href")) {
			replyPage = $(this).attr("href");
			//boardReplyController url
			getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+replyPage);
		}
		return false;
	});	
	
}

// 위의 Handle bars 템플릿에서 설정한 {{prettifyDate 값}} 에 대한 함수를 js에서 등록함.
Handlebars.registerHelper({
		"prettifyDate":function(timeValue){ // Handlbars에 날짜 출력 함수 등록
										var dateObj = new Date(timeValue);
										var year = dateObj.getFullYear();
										var month = dateObj.getMonth() + 1;
										var date = dateObj.getDate();
										return year + "/" + month + "/" + date;
		}, 
		"VisibleByLoginCheck":function(replyer){
										var result = "none";
										
										if(replyer == "${loginUser.id}") result="visible";
										
										return result;
		},
		"signActive" : function(pageNum){
										if(pageNum == replyPage) return 'active';
		}
});
// json 형태

function printData(replyArr, target, templateObject){ // jsonArrayData, 어디에 뿌릴지, 템플릿 데이터
	var template = Handlebars.compile(templateObject.html()); // reply-list-template
	var html = template(replyArr);
	$('.replyLi').remove();
	target.after(html);
}

// ex1) value 를 할당하는 방식 : 메모리를 먹음...(var printPagenation = function(pageMaker, target, templateObject){ )

//ex2) 프로토 타입으로 function 정의하는 방식 : 메모리를 먹지 않음.
function printPagination(pageMaker, target, templateObject){ // jsonArrayData, 어디에 뿌릴지, 템플릿 데이터
	var pageNum = new Array(pageMaker.endPage-pageMaker.startPage+1);
	
	for(var i=0;i<pageMaker.endPage-pageMaker.startPage+1;i++){
		pageNum[i] = pageMaker.startPage + i;
	}
	pageMaker.pageNum=pageNum;
	pageMaker.prevPageNum=pageMaker.startPage-1;
	pageMaker.nextPageNum=pageMaker.endPage+1;
	
	var template=Handlebars.compile(templateObject.html());
	var html = template(pageMaker);
	target.html("").html(html); //메서드 체이닝 (비우고 붙여)
}

function getPage(pageInfo){ // 비동기 호출
	$.getJSON(pageInfo, function(data){
		printData(data.replyList,$('#repliesDiv'),$('#reply-list-template'));
		printPagination(data.pageMaker,$('ul#pagination'),$('#reply-pagination-template'));
	});
}

function replyRegist_go(){
// 	alert("add reply btn");
	
	var replyer=$('#newReplyWriter').val();
	var replytext=$('#newReplyText').val();
	var bno = $('input[name="bno"]').val();
	
	if(!(replyer && replytext)){ // replyer , replytext 존재여부 확인
		alert("작성자 혹은 내용은 필수입니다.");
		return;
	}
	
	// data 내 bno를 board.bno를 주어도 됨.
	// bno 앞에 ""를 붙인 이유는 문자 형으로 하겠다 라는 것을 명시적으로 표시한 것.
	var data = {
			"bno" : ""+bno+"",
			"replyer":replyer,
			"replytext":replytext
	}
	
	//boardReplyController url
	$.ajax({
		url:"<%=request.getContextPath()%>/replies",
		type:"post",
		data:JSON.stringify(data),	
		contentType:'application/json',
		success:function(data){
			var result=data.split(',');
			if(result[0]=="SUCCESS"){
				alert('댓글이 등록되었습니다.');
				replyPage=result[1]; //페이지이동
				getPage("<%=request.getContextPath()%>/replies/"+bno+"/"+result[1]); //리스트 출력
				$('#newReplyText').val("");				
			}else{
				alert('댓글이 등록을 실패했습니다.');	
			}
		}
	});
}


function replyModifyModal_go(rno){
// 	alert(rno+"클릭");
// 	var replyer=$('.'+rno+'-a').attr("data-replyer");
// 	var replytext=$('div#'+rno+'-replytext').text();
	
	//alert("rno : " + rno + "\nreplyer:"+replyer+"\nreplytext:"+replytext);
	$('#replytext').val($('div#'+rno+'-replytext').text());
	$('.modal-title').text(rno);
}
function replyModify_go(){
	var rno=$('.modal-title').text();
	var replytext=$('#replytext').val();
	
	var sendData={
			replytext:replytext
	}
	
	//boardReplyController url (path variable 연습)
	//type : put,fatch, delete 인식이 브라우저마다 다름.
	//headers : 브라우저가 put, fatch 처리 위해 설정
	$.ajax({
		url:"<%=request.getContextPath()%>/replies/"+rno,
		type:"put",
		headers:{
			"X-HTTP-Method-Override":"PUT"
		},
		data:JSON.stringify(sendData),
		contentType:'application/json',
		success:function(data){
				alert('댓글이 수정되었습니다.');
				getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+replyPage); // 리스트 출력
		},
		error:function(error){ // throw 하면 error 코드를 부여하지 않으므로 주의!
			alert('수정 실패했습니다.');
		},
		complete:function(){ // 200 500 상관없이 수행됨
			$('#modifyModal').modal('hide');
		}
	});
}

function replyRemove_go(){
	var rno=$('.modal-title').text();
	
	//boardReplyController url (path variable 연습)
	//type : put,fatch, delete 인식이 브라우저마다 다름.
	//headers : 브라우저가 put, fatch 처리 위해 설정
	$.ajax({
		url:"<%=request.getContextPath()%>/replies/${board.bno}/"+rno+"/"+replyPage,
		type:"delete",
		headers:{
			"X-HTTP-Method-Override":"delete"
		},
		success:function(page){
				alert('삭제되었습니다.');
				getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+page); // 리스트 출력
				replyPage=page;
		},
		error:function(error){ // throw 하면 error 코드를 부여하지 않으므로 주의!
			alert('삭제 실패했습니다.');
		},
		complete:function(){ // 200 500 상관없이 수행됨
			$('#modifyModal').modal('hide');
		}
	});
}

</script>

