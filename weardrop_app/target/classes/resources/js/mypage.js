function pm_check() {
	$('#form_pwcheck').submit();
}

function view() {
	var value = $('#view').text();
	if(value == '보기') {		
		$('#userpw').attr("type", "text");
		$('#view').text("숨기기");
	} else {
		$('#userpw').attr("type", "password");
		$('#view').text("보기");
	}	
}

function item_check(item) {	
	 var result = validate(item);
	 if( result.code != 'valid' ) { 
		alert( result.desc );
		$('[name=' + item + ']').focus();
		return false; 
		} else { return true; }
}

function usable_email(){
	// DB에 해당 이메일로 가입한 회원이 있는지의 여부를 판단
	var result = validate('email');
	if( result.code !== 'valid' ){
		alert( result.desc );
		return;
	}
	$.ajax({	
		type: 'post',
		url: 'email_check_main',
		data: { email : $('[name=email]').val() },
		success: function(data){
			var result = join.email_usable(data);
			$('#email_status').text(result.desc);
			$('#email_status').removeClass('valid invalid');
			//usable -> valid, unusable -> invalid
			$('#email_status').addClass( 
					result.code==='usable' ? 'valid' : 'invalid' );
			$('#email_check').val( result.code );
		},
		error: function(req, text){
			alert(text + ": "+req.status);
		}
	});	
}

function validate(tag) {
	// 태그에 입력된 값이 유효한 조합인지 판단
	var result = $('[name=' + tag + ']').val();
	if ( tag === 'userpw') {
		// 영문 대, 소문자, 숫자 모두 포함
		result = join.pw_status(result);
	} else if ( tag === 'userpw_ck') {
		// 비밀번호와 비밀번호 확인이 같은지 확인
		result = join.pw_ck_status(result, $('[name=userpw]').val());
	} else if ( tag === 'email' ) {
		result = join.email_status(result);
	}
	if(result) { $('#' + tag + '_status').text(result.desc); }
	$('#' + tag + '_status').removeClass('valid invalid');
	$('#' + tag + '_status').addClass(result.code==='valid' ? 'valid' : 'invalid' );	
	
	return result;
}