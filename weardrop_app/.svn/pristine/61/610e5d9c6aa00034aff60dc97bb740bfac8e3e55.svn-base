<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" 
			href="css/common.css?v=<%=new Date().getTime()%>">	
<title>Insert title here</title>
<style type="text/css">
#comment_regist div { float: left; width: 50%; }
body {
	text-align: center;
}
h3 {
	margin-top: 150px;
}
</style>
</head>
<body>
<h3>중고장터 상세화면</h3>
<table>
<tr><th style="width: 120px;">제목</th>
	<td colspan="7" class="left">${vo.title}</td>
</tr>
<tr><th>작성자</th>
	<td>${vo.writer}</td>
	<th style="width: 12%">작성일자</th>
	<td style="width: 12%">${vo.writedate}</td>
	<th style="width: 8%">가격</th>
	<td style="width: 10%">${vo.price}</td>
	<th style="width: 10%">조회수</th>
	<td style="width: 8%">${vo.readcnt }</td>
<%-- 	<td style="width: 8%">${vo.readcnt}</td> --%>

</tr>
<tr><th>내용</th>
	<td colspan="7" class="left">${fn: replace(vo.content, crlf, '<br>')}<br>
         <c:if test="${!empty vo.filename }"> 
         <img src="<c:url value='/'/>${vo.filepath }" style="width:300px; height:300px;"/> 
         </c:if> 
	</td>
</tr>
<tr><th>첨부파일</th>
	<td colspan="7" class="left">${vo.filename}
		${ empty vo.filename ? '' :  '<img id="file-download" src="img/download.png" class="file-img"/>' }
	</td>
</tr>
</table><br>
<a class="btn blue" onclick="$('#detail').submit()">목록으로</a>

 <!-- 글을 작성한 로그인 사용자가 수정,삭제 권한을 갖는다 -->
<c:if test="${info_login.userid eq vo.userid}">
<a class="btn blue" onclick="location='modify.se?id=${vo.id}&code=${vo.code}'">수정</a>
<a class="btn green" onclick="salcomp()" id="sc">판매완료</a> 
<a class="btn red" onclick="if( confirm('정말 삭제?') ){ location='delete.se?id=${vo.id}' }">삭제</a>
</c:if> 

<form id="detail" method="post" action="list.se">
	<input type="hidden" name="curPage" value="${page.curPage}"/>
	<input type="hidden" name="search" value="${page.search}"/>
	<input type="hidden" name="keyword" value="${page.keyword}"/>
</form><br>

<div id="comment_regist" style="text-align: center; margin: 0 auto; width: 500px;">
	<div class="left"><strong>댓글작성</strong></div>
	<div class="right"><a class="btn green"
				onclick="go_comment_regist()">등록</a></div>
	<textarea id="comment" style="margin-top: 5px; width: 99%; height: 80px; "></textarea>
</div>
<div id="comment_list" style="text-align: left; margin: 0 auto; width: 500px;">
</div>

<script>
$(document).ready(function() {
	comment_list();
})
// 댓글이 보여주는 처리
function comment_list() {
	$.ajax({
		url: 'sell/comment/${vo.id}',
		success: function(result) {
			$('#comment_list').html(result);
		},error: function(req, text) {
			alert(text + ": "+ req.status);
		}
	});
}	
function go_comment_regist() {
	// 로그인 되어 있어야 댓글 저장 가능
	if( ${empty info_login} ){
		alert('댓글을 등록하려면 로그인을 하세요!');
		return;
	}
	// 입력한 글이 있어야 댓글저장
	if($('#comment').val().trim()==='' ) {
		alert("댓글을 입력하세요!");
		$('#comment').val('');
		$('#comment').focus();
		return;
	}
	$.ajax({
		data: {pid: ${vo.id}, content: $('#comment').val()},
		url: "sell/comment/insert",
		success: function (data) {
			if(data){
				alert('댓글이 등록되었습니다.');
				$('#comment').val('');
				comment_list();
			}else{
				alert('댓글 등록 실패!');
			}
		}, error: function (req, text) {
			alert( text + ": " + req.status);
		}
	});
}
	$(function () {
		comment_list();
		$('#file-download').on('click', function() {
			location='download.se?id=${vo.id}';
		});
	});
	
/* 	function salcomp() {		// 판매완료
		$('#')
		location='modify.se?id=${vo.id}&code=${vo.code};
	} */
	
 	$(function salcomp() {
 		
		$('#sc').click(function (trade) {
			
			location.href='list.se';
		});
	});
	

</script>
</body>
</html>