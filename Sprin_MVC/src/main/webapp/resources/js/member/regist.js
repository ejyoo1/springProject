/**
 * picture_go : 사진 용량, 확장자 체크 
 * upload_go : 회원 등록 썸네일 업로드 체크
 */
var formData = "";

// 사진 업로드 --------------------------------------------------------------------
function picture_go(){
	formData = new FormData($('form[role="imageForm"]')[0]); 
	var form = $('form[role="imageForm"]');
	var picture = form.find('[name=pictureFile]')[0];
	
	// 업로드 확인 변수 초기화
	form.find('[name="checkUpload"]').val(0);
	var fileFormat = 
		picture.value.substr(picture.value.lastIndexOf(".")+1).toUpperCase();
	
	// 이미지 확장자 jpg 확인
	if(!(fileFormat=="JPG" || fileFormat=="JPEG")){
		alert("이미지는 jpg/jpeg 형식만 가능합니다.");
		picture.value="";
		return;
	}
	
	// 이미지 파일 용량 체크
	if(picture.files[0].size>1024*1024*1){ 
		alert("사진 용량은 1MB 이하만 가능합니다.");
		return;
	}
	
// 	alert("성공");
	document.getElementById('inputFileName').value=picture.files[0].name; 
	
	if(picture.files && picture.files[0]){ 
		var reader = new FileReader(); 
		reader.onload = function (e) { 
			$('div#pictureView')
				.css({'background-image':'url('+e.target.result+')',
							'background-position':'center',
							'background-size':'cover',
							'background-repeat':'no-repeat'
						});
		}
		reader.readAsDataURL(picture.files[0]); 
	}
}

function upload_go(){
// 	alert("upload btn click");
	if($('input[name="pictureFile"]').val()==""){
		alert("사진을 선택하세요.");
		$('input[name="pictureFile"]').click();
		return;
	}
	
	// 사진이 등록된 것이 있다면 등록 불가하게 유효성 체크
	if($('input[name="checkUpload"]') == 1){
		alert("이미 업도드된 사진입니다.");
		return;
	}
	
	$.ajax({
		url : "picture.do",
		data : formData,
		type : 'post',
		processData : false,
		contentType : false,
		success : function(data){
			// 업로드 확인 변수 세팅
			$('input[name="checkUpload"]').val(1);
			
			// 저장된 파일명 저장.
			$('input#oldFile').val(data); 
			$('form[role="form"] input[name="picture"]').val(data);
			
			alert("사진이 업로드 되었습니다.");
		},
		error : function(error){
			alert("현재 사진 업로드가 불가합니다. \n 관리자에게 연락바랍니다.");
		}
	});
}


// 아이디 중복 체크------------------------------------------------------
var checkedID = "";
function idCheck_go(){
//		alert("중복 확인 버튼 클릭");
	var input_ID=$('input[name="id"]');
	
	if(input_ID.val() == ""){
		alert("아이디를 입력하세요");
		input_ID.focus();
		return;
	} else {
		// 아이디는 4~12자의 영문자와 숫자로만 입력
		var reqID = /^[a-z]{1}[a-zA-Z0-9]{3,12}$/;
		if(!reqID.test($('input[name="id"]').val())){// 유효성에 맞지 않으면
			alert("아이디는 첫글자는 영소문자이며, \n 4~13자의 영문자와 숫자로만 입력해야 합니다.");
			$('input[name="id"]').focus();
			return;
		}
	}
	
	// 유효성에 맞으면 비동기 수행
	//
	$.ajax({
		url:"idCheck.do?id=" + input_ID.val(),
		method:"get",
		success:function(result){
			console.log(result);
			if(result=="duplicated"){
				alert("중복된 아이디 입니다.");
				$('input[name="id"]').focus();
			} else {
				alert("사용가능한 아이디 입니다.");
				checkedID = input_ID.val().trim(); // 아이디 체크 상태 저장 (중복 완료된 아이디 저장)
				$('input[name="id"]').val(input_ID.val().trim()); // 사용자 아이디 입력 시 빈칸이 혹시나 있을 수 있으므로 trim
			}
		},
		error:function(error){
			alert("시스템 장애로 가입이 불가합니다.");
		}
	});
}

function regist_go(){ 
	// 데이터 유무 판단
//		alert("regist_go");
	var uploadCheck = $('input[name="picture"]').val();
	if(!uploadCheck){ // 사진 데이터 유무 체크 (uploadCheck 없으면)
		alert("사진 업로드는 필수입니다.");
		//$('input[name="pictureFile"]').click();
		return;
	}
	
	if($('input[name="id"]').val()==""){
		alert("아이디는 필수입니다");
		return;
	}
	
	if($('input[name="id"]').val() != checkedID){
		alert("아이디 중복확인이 필요합니다.");
		return;
	}
	
	if($('input[name="pwd"]').val() == ""){
		alert("패스워드는 필수입니다.");
		return;
	}
	
	if($('input[name="name"]').val() == ""){
		alert("이름은 필수입니다.");
		return;
	}
	
	var form = $('form[role="form"]');
	form.submit();
}
