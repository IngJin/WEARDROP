<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:forEach items="${list }" var="vo" varStatus="status">
${status.index eq 0 ? '<hr>' : ''}
<hr>
	<div>${vo.writer }[${vo.writedate }]</div>
	<!-- 로그인한 회원이 글을 작성한 회원인 경우 수정, 삭제 가능 -->
	<c:if test="${vo.userid eq info_login.userid}">
		<span style="float: right;"> 
		<a onclick="go_modify_save(${vo.id})" class="btn-fill-small" id="btn-modify-save-${vo.id }">수정</a> 
		<a onclick="go_delete_cancel(${vo.id})" class="btn-fill-small" id="btn-delete-cancel-${vo.id }">삭제</a>
		</span>
	</c:if>
	<div id="original-${vo.id }">${fn: replace( fn:replace(vo.content, lf, '<br>'), crlf, '<br>')  }</div><!--원래글  --><!--댓글줄바꿈처리  -->
	<div id="modify-${vo.id }" style="display: none"></div>
	<hr>
</c:forEach>

<script type="text/javascript">
function display_mode(id, mode){
	$('#modify-' + id).css('display', mode=='m' ? 'block' : 'none');
	$('#original-' + id).css('display', mode=='m' ? 'none' : 'block');
	$('#btn-modify-save-' + id).text(mode=='m' ? '저장' : '수정');
	$('#btn-delete-cancel-' + id).text(mode=='m' ? '취소' : '삭제');
}
function go_modify_save(id){
	if($('#btn-modify-save-'+id).text() == '수정'){
	var tag = "<textarea id='edit-" + id + "' style='margin-top:5px; width:99%; height:40px;'>"
				+ $('#original-' + id).html().replace(/<br>/g, '\n')
				+ "</textarea>";
	$('#modify-' + id).html(tag);	
	display_mode(id, 'm');
	}else{
		var comment = new Object();
		comment.id = id;
		comment.content = $('#edit-'+id).val();
		//alert(comment.id + ": " +comment.content);
		
		$.ajax({
			type : 'post',
			url : 'hugi/comment/update',
			data : JSON.stringify(comment),
			/* 전송할 json에 한글이 있는 경우 깨지지 않도록 처리 */
			contentType: 'application/json; charset=utf-8',
			success: function(data){
				alert('댓글변경' + data)
				comment_list();
			},
			error: function(req, text){
				alert(text + ": " + req.status);
			}
		});
	}
}
function go_delete_cancel(id){
	if($('#btn-delete-cancel-'+id).text() == '취소')
		display_mode(id,'d');
	else{
		if(confirm('정말 삭제하시겠습니까?')){
			$.ajax({
				url: 'hugi/comment/delete/'+id,
				success: function(){
					comment_list();
				},
				error: function(req, text){
					alert(text + ": " + req.status);
				}
			});
		}
	}
}
</script>