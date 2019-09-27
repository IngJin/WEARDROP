<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>		<!--jquery 선언-->
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
text-align: center;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	comment_list();
})

$(function(){
	$('#file-download').on('click', function(){
		location='download.hu?id=${vo.id}';
	});
});

function go_comment_regist(){
	//로그인되어 있어야 댓글저장 가능
	if(${empty info_login}){
		alert('댓글을 등록하려면 로그인하세요!');
		return;
	}
	//입력한 글이 있어야 댓글저장
	if($('#comment').val().trim() === ''){
		alert('댓글을 입력하세요!!');
		$('#comment').val('');
		$('#comment').focus();
		return;
	}
	
	$.ajax({
		data: { pid : ${vo.id}, content: $('#comment').val() },
		url : "hugi/comment/insert",
		success: function(data){
			if(data){
				alert('댓글이 등록되었습니다.');
				$('#comment').val('');
				comment_list();
			}else{
				alert('댓글 등록이 실패되었습니다.');
			}
		},
		error: function(req, text){
			alert(text+ ": " + req.status);
		}
	});
}

//댓글이 보여지는 처리
function comment_list(){
	$.ajax({
		url: 'hugi/comment/${vo.id}',
		success: function(result){
			$('#comment_list').html(result);
		},
		error: function(req, text){
			alert(text + ":" + req.status);
		}
	});
}
</script>
</head>
<body>
<h3>후기 상세화면</h3>
<table>
		<tr>
			<th style="width : 120px;">제목</th>
			<td>${vo.title }</td>
			<th >조회수</th>
			<td >${vo.readcnt }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.writer }</td>
			<th colspan="3" style="width:30%">작성일자</th>
			<td style="width:30%">${vo.writedate }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="5" >${fn: replace(vo.content, crlf, '<br>')}</td>
			<%-- <img src="<c:url value='/'/>${vo.filepath}">  내용에 사진 나오게 하기 --%>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan="5">${vo.filename }
			<!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
			${empty vo.filename ? '' : '<img id="file-download" src = "img/download.png" class="file-img"/>'}
			</td>
		</tr>
	</table><br>
	<a onclick="location='list.hu'">목록으로</a>

	
	<a onclick="location='modify.hu?id=${vo.id }'">수정</a>
	<a onclick="if(confirm('정말 삭제하시겠습니까?')){location='delete.hu?id=${vo.id}' }">삭제</a>
	<a >좋아요</a>

	<!--댓글 작성 -->
	<div id="comment_regist" style="text-align: center; margin: 0 auto; width: 800px; margin-top: 20px;">
		<textarea id="comment" style="width: 700px; height: 35px; margin-left: -69px;" placeholder="로그인 후 댓글을 남겨주세요."></textarea>
		<div class="right" style="margin-left: 700px; margin-top: -38px;">
			<a class="registbtn" onclick="go_comment_regist()">등록</a>
		</div>
	</div>
	<!--댓글 리스트  -->
	<div id="comment_list" style="text-align: left; margin: 0 auto; width: 770px; margin-top: 30px;"></div>
</body>
</html>