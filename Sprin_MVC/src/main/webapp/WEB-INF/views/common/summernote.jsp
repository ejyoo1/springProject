<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script>
function Summernote_start(targetObj){// notice/regist
	targetObj.summernote({
		placeholder:'여기에 내용을 적으세요.',
		height:250,
		disableResizeEditor: true,
		//backspace로 지우는것은 js인터럽트가 아니므로 이미지 지워지지 않음
		// 문제점 callback 함수 작성한 뒤에 아무것도 설정하지 않으면 이미지가 등록되지 않음.
		callbacks:{
			onImageUpload : function(files, editor, welEditable) {
				//file size check!
				for (var file of files) {
					if(file.size > 1024*1024*5){
	            		alert("이미지는 5MB 미만입니다.");
	            		return;
					}	
					if(file.name.substring(file.name.lastIndexOf(".")+1).toUpperCase() != "JPG"){
						alert("JPG 이미지형식만 가능합니다.");
						return;
					}
				}
				
				for (var file of files) {
					sendFile(file,this); // file과 this를 주는이유 ? 이벤트 발생 시 이벤트 대상(써머노트) sendFIle Function은 서버노트 Editor를 가지고 이미지를 추가
				}
				// 파일 수 제한은 하지 않음. 만약 제한 걸고 싶다면 file.length를 잡기 => file.length > 5
			},
			onMediaDelete : function(target) {
				alert(target[0].src.split("=")[1]); // target[0] : img 태그
				//alert(target[0].src.split("=")[1]);
				var answer=confirm("정말 이미지를 삭제하시겠습니까?");
				if(answer){
					deleteFile(target[0].src);
				}
			}// summernote 라이브러리 사용 / disableResizeEditor : 리사이즈 막음.
		}
	});
}

function sendFile(file, el) { // uploadImg.do ==> REST Handler 넘겨줄 view 없음.
	
	var form_data = new FormData();
	form_data.append("file", file); 
	$.ajax({
    	data: form_data,
    	type: "POST",
    	url: '<%=request.getContextPath()%>/uploadImg.do',
    	cache: false,
    	contentType: false,	    	
    	processData: false,
    	success: function(img_url) {
      		$(el).summernote('editor.insertImage', img_url);
    	},
    	error:function(){
    		alert("이미지 업로드에 실패했습니다.");
    	}
  	});
}

function deleteFile(src) {
	var splitSrc= src.split("=");
	var fileName = splitSrc[splitSrc.length-1];
	
	var fileData = {fileName:fileName};
	
	$.ajax({
		url:"<%=request.getContextPath()%>/deleteImg.do",
		data : JSON.stringify(fileData),
		type:"post",
		contextType:"application/json",
		success:function(res){ // 200 이면 잘 지워진 것.
			console.log(res);
		},
		error:function(){
			alert("이미지 삭제가 불가합니다.");
		}
	});
}
</script>
