<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	function necessary() {
		var need = true; /*반환할 변수 선언  */
		$('.need').each(function() {
			if ($(this).val() == '') {
				alert($(this).attr('title') + ' 입력하세요!');
				$(this).focus(); /*포커스 지정  */
				need = false;
				return need;
			}
		});
		return need;
	}
	
	$(function(){													/*첨부파일 이미지 옆에 파일 이름 뿌리기 */
		$('#attach-file').on('change', function(){
			$('#file-name').html(this.files[0].name);
			$('#file-name').css('padding-right', '15px');			/*파일 이름과 이미지 사이 간격 처리  */
			$('#delete-file').css('display', 'inline-block');		/*첨부파일 선택한 후 취소 이미지 올라오기  */
			$('#select_img').css('display', 'block');
		});
		$('#delete-file').on('click', function(){					/*첨부파일 취소 이미지 클릭했을 경우(원래상태로 돌아가기)*/
			$('#attach-file').val('');								/*파일 선택 이미지 초기화  */
			$('#file-name').html('');								/*파일 이름 초기화  */
			$('#file-name').css('padding-right', '0px');			/*파일 이름과 이미지 사이 간격 초기화  */
			$('#delete-file').css('display', 'none');				/*취소 이미지 안보이게 처리  */
			$('#select_img').css('display', 'none');
		});
	});


</script>
</head>
<body>
	<h3>신규게시글</h3>
	<form action="insert.com" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<th>유형</th>
				<td><select style="width: 20%; height: 28px; float: left"
					id="code" name="code">
						<option>유형을 선택하세요</option>
						<option value=1>세일정보</option>
						<option value=2>자유글</option>
				</select></td>
			</tr>
			<tr>
				<th style="width: 20%">제목</th>
				<td><input type="text" class="need" title="제목" id="title"
					name="title" style="width: 98.5%" /></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td class="left">
				<!-- <input type="text" class="need" title="작성자" id="writer" name="writer" style="width: 98.5%" /> -->
				${info_login.writer}
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea style="width: 99%; height: 200px" class="need"
						title="내용" id="content" name="content"></textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td class="left">
				<img onclick="$('#attach').val('y')" src="img/delete.png" id="delete-file" class="file-img" />
					 <label id="file-name">${vo.filename }</label><!--첨부된 파일의 이름  --> 
				<label for="gdsImg"> <img src="img/select.png" class="file-img" /> 
				<input type="file" name="file" id="attach-file" class="gdsImg" />
						<div id="select_img"><img src="" /></div>	<!--썸네일  -->		
				</label></td>
			</tr>
		</table>
		<br> <a onclick="if(necessary()){$('form').submit()}">저장</a> <a
			onclick="location='list.com'">취소</a>
	</form>
	<!--첨부파일 다운로드 시 썸네일  -->
	<script type="text/javascript">
	$(".gdsImg").change(
			function() {
				if (this.files && this.files[0]) {
					var reader = new FileReader;
					reader.onload = function(data) {
						$("#select_img img").attr("src", data.target.result)
								.width(500);
					}
					reader.readAsDataURL(this.files[0]);
				}
			});
	</script>
	
</body>
</html>