<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/common.css?v=<%= new Date().getTime() %>">
<style type="text/css">
body {
	text-align: center;
}
</style>
</head>
<body>
<h3>중고장터 내용 수정</h3>

<form action="update.se" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="${vo.id}"/>

<table border="1">
<tr><th style="width:20%">제목</th>
	<td><input value="${vo.title}" type="text" class="need" title="제목" name="title" style="width: 98.5%"/></td>
</tr>
<tr><th>작성자</th>
	<td class="left">${vo.writer}</td>
</tr>
<tr><th>가격</th>
	<td><input value="${vo.price}" type="number" class="need" title="가격" name="price" style="width: 98.5%"/></td>
</tr>
<tr><th>내용</th>
	<td><textarea title="내용" class="need" 
	style="width: 99%; height: 150px" name="content">${vo.content}</textarea>
	</td>
</tr>
<tr><th>첨부파일</th>
	<td class="left">
		<img onclick="$('#attach').val('y')" src="img/delete.png" id="delete-file" class="file-img"/>
		<label id="file-name">${vo.filename}</label>
		<label>
			<img src="img/select.png" class="file-img"/>
			<input type="file" name="file" id="attach-file"/>
		</label>
	</td>
</tr>
</table><br>
<input type="hidden" name="attach" id="attach"/>
<a class="btn blue" onclick="if( necessary() ){ $('form').submit() }">저장</a>
<a class="btn red" onclick="location='detail.se?id=${vo.id}&code=${vo.code }'">취소</a>
</form>
<script type="text/javascript">
$(function(){
// 	if( '${ !empty vo.filename }'=='true' ){
	if( '${ !empty vo.filename}' == 'true' ){
		$('#delete-file').css('display', 'inline-block');
		$('#file-name').css('padding-right', '15px');
	}
});
</script>


</body>
</html>