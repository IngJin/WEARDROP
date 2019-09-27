<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/common.css?v=<%= new Date().getTime() %>">
<link href="https://fonts.googleapis.com/css?family=Indie+Flower&display=swap" 
rel="stylesheet">
<style type="text/css">
body {
	text-align: center;
}
.sell {
margin-left : 200px;
}
</style>
</head>
<body>
<h3>신규글</h3>
<!-- 파일첨부
1, form 태그의 method는 반드시 post 로 지정한다. 
2, enctype 속성을 지정한다. multipart/form-data
 -->
 <div class = "sell">
 <form action="insert.se" method="post" enctype="multipart/form-data">
 <input type='radio' id='code' name='code' value='1' />삽니다
  <input type='radio' id='code' name='code' value='2' checked="checked"/>팝니다
    <br>
 <table border="1">
	 <tr><th style="width: 20%">제목</th>
	 	<td><input type="text" name="title" style="width: 98.5%"></td></tr>
	 <tr><th>작성자</th>
	 	<td class="left">${info_login.writer}</td></tr>
	 <tr><th>판매가격</th>
	 	<td><input type="text" style="width: 98.5%" name="price"></td></tr>
	 <tr><th>내용</th>
	 	<td><textarea title="내용" class="need" style="width: 99%; height: 200px" name="content"></textarea></td>
	 	</tr>
	 <tr><th>첨부파일</th>
	<td class="left">
		<img src="img/delete.png" id="delete-file" class="file-img"/>
		<label id="file-name"></label>
		<label>
			<img src="img/select.png" class="file-img"/>
			<input type="file" name="file" id="attach-file"/>
		</label>
	</td>
</tr>
</table><br>
 <a class="btn blue" onclick="if(necessary() ){ $('form').submit() }">저장</a>
 <a class="btn red" onclick="location='list.se'">취소</a>
 </form>
 </div>
 
</body>
</html>