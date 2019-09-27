var join = {
	common: {
		empty: { code:'empty', desc:'데이터를 입력하세요' },
		space: { code:'space', desc:'공백 없이 입력하세요'},
		max: { code:'max', desc:'최대 10자 이하로 입력하세요' },
		min: { code:'min', desc:'최소 5자 이상 입력하세요' },
	},	
	id: {
		valid: { code:'valid', desc: '아이디 중복확인하세요.'},
		invalid: { code:'invalid', desc: '아이디는 영문소문자와 숫자만 입력 가능합니다.'},
		usable: { code:'usable', desc:'사용가능한 아이디입니다'},
		unusable: { code:'unusable', desc:'이미 사용중인 아이디입니다'},
	},
	pw:{
		valid: { code:'valid', desc:'사용가능한 비밀번호입니다.' },
		invalid: { code:'invalid', desc:'비밀번호는 영문 대,소문자와 숫자만 사용 가능합니다.'},
		lack: { code:'lack', desc:'영문 대/소문자, 숫자를 모두 포함해야만 합니다.'},
		equal: { code:'valid', desc:'비밀번호가 일치합니다' },
		notequal: { code:'invalid', desc:'비밀번호가 일치하지 않습니다'}
	},
	email:{
		valid: { code:'valid', desc:'이메일 중복확인하세요.' },
		invalid: { code:'invalid', desc:'유효하지 않은 이메일입니다.' },
		usable: { code:'usable', desc:'사용가능한 이메일입니다'},
		unusable: { code:'unusable', desc:'이미 사용중인 이메일입니다'},
	},	
	id_usable: function(usable){
		if( usable === 'true' ) { return this.id.usable; }
		else { return this.id.unusable;}
	},	
	email_usable: function(usable){
		if( usable === 'true' ) { return this.email.usable; }
		else { return this.email.unusable;}
	},	
	pw_ck_status: function(pw_ck, pw){
		if( pw_ck === '' ) { return this.common.empty; }
		else if( pw_ck === pw ) {	return this.pw.equal; }
		else { return this.pw.notequal; }
	},
	pw_status: function(pw){
		var space = /\s/g;
		var reg = /[^a-zA-Z0-9]/g;
		var upper = /[A-Z]/g,   lower = /[a-z]/g,    digit = /[0-9]/g;
		if(pw === ''){ return this.common.empty; }
		else if( pw.match(space) )	 { return this.common.space; }
		else if( reg.test(pw) ) { return this.pw.invalid; }
		else if( pw.length < 5 ) { return this.common.min; }
		else if( pw.length > 10 ) { return this.common.max; }
		else if( !upper.test(pw) || !lower.test(pw) || !digit.test(pw) ) { return this.pw.lack; }
		else {return this.pw.valid; }
	},
	id_status: function(id){
		var space = /\s/g;
		var reg = /[^a-z0-9]/g;
		if(id === '')	{ return this.common.empty; }
		else if( id.match(space) ) { return this.common.space; }
		else if( reg.test(id) ) { return this.id.invalid; }
		else if( id.length < 5 ) { return this.common.min; }
		else if( id.length > 10 ) { return this.common.max; }
		else { return this.id.valid; }
	},
	email_status: function(email){
		var space = /\s/g;
		var reg = /^[_\.0-9a-zA-Z-]+@([0-9a-zA-Z][0-9a-zA-Z-]+\.)+[a-zA-Z]{2,6}$/i;
		if(email === '')	{ return this.common.empty; }
		else if( email.match(space) ) { return this.common.space; }
		else if( !reg.test(email) )	 { return this.email.invalid; }
		else { return this.email.valid; }
	}
		
}