function go_join() {
    // 아이디 중복확인 하지 않은 경우
    if ($('#id_check').val() === 'n') {
        if (!item_check('userid')) {
            return;
        } else {
            // 중복확인 하도록 유도
            alert(join.id.valid.desc);
            $('#btn-id').focus();
            return;
        }
    } else if ($('#id_check').val() === 'unusable') {
        // 아이디 중복확인 한 경우 : usable/usable
        alert(join.id.unusable.desc);
        // $('[name=userid]').val('');
        $('[name=userid]').focus();
        return;
    }

    if ($('[name=writer]').val().trim() === '') {
        alert('닉네임을 입력하세요.');
        $('[name=writer]').focus();
        $('[name=writer]').val('');
        return;
    }

    // 안내문구에도 불구하고 유효하지 않은 형태의 데이터로
    // 회원가입할 수 있으므로 유효성부터 판단.
    if (!item_check('userpw')) {
        return;
    }
    if (!item_check('userpw_ck')) {
        return;
    }

    // 이메일 중복확인 하지 않은 경우
    if ($('#email_check').val() === 'n') {
        if (!item_check('email')) {
            return;
        } else {
            // 중복확인 하도록 유도
            alert(join.email.valid.desc);
            $('#btn-email').focus();
            return;
        }
    } else if ($('#email_check').val() === 'unusable') {
        // 이메일 중복확인 한 경우 : usable/usable
        alert(join.email.unusable.desc);
        $('[name=email]').focus();
        return;
    }

    if (!($('input:checkbox[name="terms"]').is(":checked"))) {
        alert('이용약관에 동의해주세요.');
        return;
    }

    $('#from_join').submit();
}

function item_check(item) {
    var result = validate(item);
    if (result.code != 'valid') {
        alert(result.desc);
        $('[name=' + item + ']').focus();
        return false;
    } else {
        return true;
    }
}

function usable() {
    var result = validate('userid');
    if (result.code !== 'valid') {
        alert(result.desc);
        return;
    }
    // DB에 해당 아이디로 가입한 회원이 있는지의 여부를 판단
    $.ajax({
        type: 'post',
        url: 'id_check_main',
        data: {
            userid: $('[name=userid]').val()
        },
        success: function(data) {
            var result = join.id_usable(data);
            $('#userid_status').text(result.desc);
            $('#userid_status').removeClass('valid invalid');
            //usable -> valid, unusable -> invalid
            $('#userid_status').addClass(
                result.code === 'usable' ? 'valid' : 'invalid');
            $('#id_check').val(result.code);
        },
        error: function(req, text) {
            alert(text + ": " + req.status);
        }
    });
}


function usable_email() {
    // DB에 해당 이메일로 가입한 회원이 있는지의 여부를 판단
    var result = validate('email');
    if (result.code !== 'valid') {
        alert(result.desc);
        return;
    }
    $.ajax({
        type: 'post',
        url: 'email_check_main',
        data: {
            email: $('[name=email]').val()
        },
        success: function(data) {
            var result = join.email_usable(data);
            $('#email_status').text(result.desc);
            $('#email_status').removeClass('valid invalid');
            //usable -> valid, unusable -> invalid
            $('#email_status').addClass(
                result.code === 'usable' ? 'valid' : 'invalid');
            $('#email_check').val(result.code);
        },
        error: function(req, text) {
            alert(text + ": " + req.status);
        }
    });
}

function validate(tag) {
    // 태그에 입력된 값이 유효한 조합인지 판단
    var result = $('[name=' + tag + ']').val();
    if (tag === 'userid') {
        // 영문 소문자, 숫자로만 제한
        result = join.id_status(result);
    } else if (tag === 'userpw') {
        // 영문 대, 소문자, 숫자 모두 포함
        result = join.pw_status(result);
    } else if (tag === 'userpw_ck') {
        // 비밀번호와 비밀번호 확인이 같은지 확인
        result = join.pw_ck_status(result, $('[name=userpw]').val());
    } else if (tag === 'email') {
        result = join.email_status(result);
    }
    if (result) {
        $('#' + tag + '_status').text(result.desc);
    }
    $('#' + tag + '_status').removeClass('valid invalid');
    $('#' + tag + '_status').addClass(result.code === 'valid' ? 'valid' : 'invalid');

    return result;
}

function go_login() {
    $.ajax({
        type: 'post',
        url: 'login_log',
        data: {
            userid: $('#Loginid').val(),
            userpw: $('#Logindpwd').val()
        },
        success: function(data) {
            if (data) {
                location = 'index';
                alert(data.writer + "님 환영합니다!");
            } else {
                alert('아이디나 비밀번호가 일치하지 않습니다!');
                $('#Loginid').focus();
            }
        },
        error: function(req, text) {
            alert(text + ": " + req.status);
        }
    });
}

function terms() {
    window.open("terms.ho", "a", "width=1000, height=800, left=100, top=50");
}

function userid_find() {
    window.open("userid_find.ho", "a", "width=500, height=200, left=100, top=50");
}

function userpw_find() {
    window.open("userpw_find.ho", "a", "width=500, height=300, left=100, top=50");
}