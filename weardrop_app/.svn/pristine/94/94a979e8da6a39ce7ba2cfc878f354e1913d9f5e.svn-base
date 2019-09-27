<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>  
<script type="text/javascript" 	src="js/join_check.js?v=<%=new Date().getTime()%>"></script> 
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script type="text/javascript" src="js/login.js?v=<%=new Date().getTime()%>"></script> 
<style type="text/css">
.tre {
	font-size: 0.9em;
	font-family: 'Noto Sans KR', sans-serif;
}

.header_main {
	width: 175px;
	background-color: #ffffff;
	float: left;
	height: 100%;
	overflow: hidden;
}

.header_main_logo {
	width: 100px;
	line-height: 100vh;
	text-align: center;
}

.join {
	 margin-left: 10%;
}
</style>
</head>
<body>
	<div class="header_main">
		<div class="header_main_logo">
			<img src="img/logo.png" class="logo_img" alt="로고">
		</div>
	</div>
	<div class="all">
	  <div class="join">
		  <div class="login1">
		 	<h1 class="login_h1">Login</h1>
			<span>
				<div class="login_font">UserId</div></br> 
				<input class="login_list" type="text" id="Loginid"/><br/><br/>
				<div class="login_font">PassWord</div></br>
				<input class="login_list" type="password" id="Logindpwd"/><br/><br/>
			</span>	
			<div class="tre" style="margin-left: 45%;"> <a href="javascript:void(0);" onclick="userid_find()">아이디 찾기</a> / <a href="javascript:void(0);" onclick="userpw_find()">비밀번호 찾기</a> </div><br/> 
			<div class="btn_div">
				<a class="btn" onclick="go_login()">Login</a>
				<a class="btn" href="https://kauth.kakao.com/oauth/authorize?client_id=47035dbae45dcf3a446b494c2294ae92&redirect_uri=http://192.168.0.67/iot/kakaologin&response_type=code">Kakao</a>
				<a class="btn" href="${url}">Naver</a>
			</div>
		  </div>		
	  </div> 
	  <div class="join2">
   	<form method="post" action="join_log" id="from_join">
		 <input type="hidden" id="id_check" value="n">
		  <input type="hidden" id="email_check" value="n">		 
		  <div class="login2">
		 	<h1 class="login_h1">Register</h1>
			<span>
				<div class="login_font">UserId</div>
					<input class="login_list" type="text" id="userid" name="userid" onkeyup="$('#id_check').val('n'); validate('userid')"/>				
					<input class="login_button" id="btn-id" type="button" value="Duplicate" onclick="usable()"/><br/>
					<div class="valid" id="userid_status">아이디를 입력하세요.(영문 소문자, 숫자만 입력 가능합니다)</div></br>
					<div class="login_font">Nickname</div>
					<input class="login_list" type="text" id="writer" name="writer"/><br/><br/>
					<div class="login_font">PassWord</div>
					<input class="login_list" type="password" id="userpw" name="userpw" onkeyup="validate('userpw')"/><br/>
					<div class="valid" id="userpw_status">비밀번호를 입력하세요.(영문 대문자, 소문자, 숫자를 모두 입력하셔야 합니다)</div></br>
					<div class="login_font">PassWord_Check</div>
					<input class="login_list" type="password" id="userpw_check" name="userpw_ck" onkeyup="validate('userpw_ck')"/><br/>
					<div class="valid" id="userpw_ck_status">비밀번호를 다시 입력하세요.</div><br/>				
					<div class="login_font">Email</div>
					<input class="login_list" type="email" id="email" name="email" onkeyup="$('#email_check').val('n'); validate('email')"/>
					<input class="login_button" id="btn-email" type="button" value="Duplicate" onclick="usable_email()"/><br/>
					<div class="valid" id="email_status">이메일을 입력하세요</div><br/>
					<br/>					
			</span><br/>	
			<div class="tre"><input type="checkbox" name="terms" style="width:15px;height:15px;"> 본 서비스 <a href="javascript:void(0);" onclick="terms()" style="text-decoration:underline;">이용약관</a>에 동의합니다.</div><br/>
			<div class="btn_div">
				<a class="btn" onclick="go_join()">Register</a>
			</div>
		  </div>			
		</form>
	  </div> 
	</div>
</body>
</html>