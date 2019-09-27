<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>		<!--jquery 선언-->
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
ul.tabs {
	margin: 0;
	padding: 0;
	float: left;
	list-style: none;
	height: 41px;
	border-left: 1px solid #eee;
	width: 100%;
	font-family: "dotum";
	font-size: 15px;
}

ul.tabs li {
	float: left;
	text-align: center;
	cursor: pointer;
	width: 120px;
	height: 39px;
	line-height: 31px;
	border: 1px solid #eee;
	border-left: none;
	font-weight: bold;
	background: #fafafa;
	overflow: hidden;
	position: relative;
}

ul.tabs li.active {
	background: #FFFFFF;
	border-bottom: 1px solid #FFFFFF;
}

.tab_container {
	border-top: 1px solid black;
	clear: both;
	float: left;
	width: 1000px;
	height: 500px;
	background: #FFFFFF;
}

.tab_content {
	margin-top: 10px;
	padding: 5px;
	font-size: 15px;
	display: none;
}

#container {
	width: 249px;
	margin-left: 180px;
	margin-top: 80px;
}

#table {
	border: hidden;
	margin-top: -10px;
}

#table th, #table td {
	border: hidden;
}

.search{
	float: right;
}
</style>
<script type="text/javascript">
	$(function() {

		$(".tab_content").hide(); //모든 탭 숨기기
		$(".tab_content:first").show(); //첫번째 탭 내용 보여주기

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active").css("color", "#333");
			//$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
			$(this).addClass("active").css("color", "darkred");
			$(".tab_content").hide()

			var activeTab = $(this).attr("rel");
			$("#" + activeTab).fadeIn()
		});
	});
</script>
</head>
<body>
	<h3>커뮤니티</h3>
	<form method="post" action="list.com" id="list">
	<div id="container">
		<!--탭 메뉴 영역  -->
		<div style="width: 100vh;">
		<ul class="tabs">
			<li class="active" rel="tab1">세일정보</li>
			<li rel="tab2">자유글</li>
		<span class="search" style="margin-right: -30px;">
			<select  name="search" style="height: 28px; width: 92px;">
				<option value="all" ${page.search eq 'all' ? 'selected' : '' }  >전체</option>
				<option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
				<option value="content" ${page.search eq 'content' ? 'selected' : '' }>내용</option>
				<option value="writer" ${page.search eq 'writer' ? 'selected' : '' }>작성자</option>
			</select>
			<input type="text" name="keyword" value="${page.keyword }" style="width:350px;"/> 
				<a onclick="$('form').submit()">검색</a>
		</span>
		</ul>
		</div>
		
		<!--탭 콘텐츠 영역-->
		<div class="tab_container">
			<!--첫번째 탭 내용  -->
			<div id="tab1" class="tab_content">
				<table id="table" style="width: 100%">
					<tr>
						<th>번호</th>
						<th style="width: 450px;">제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
						<th>추천수</th>
					</tr>
					<% 
							int i = 1;
					%>
					<c:forEach items="${page.list }" var="vo">
					<c:if test="${vo.code == 1 }">
					
					<tr>
						<%-- <td>${vo.no }</td> --%>
						
						<td><%= i++ %></td>
						<td><a onclick="location='detail.com?id=${vo.id}&read=1'">${vo.title }
						<!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
						${empty vo.filename ? '' : '<img src="img/image.png" class="file-img"/>' }
						</a></td>
						<td>${vo.writer }</td>
						<td>${vo.writedate }</td>
						<td>${vo.readcnt }</td>
						<td>${vo.likecnt }</td>
					</tr>
					</c:if>
				</c:forEach>				
				</table>
			</div>
			<!-- 두번째 탭 내용 -->
			<div id="tab2" class="tab_content">
				<table id="table" style="width: 100%">
					<tr>
						<th>번호</th>
						<th style="width: 450px;">제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
						<th>추천수</th>
					</tr>
					<% 
							int k = 1;
					%>
					<c:forEach items="${page.list }" var="vo">
					<c:if test="${vo.code == 2 }">		
					<tr>
						<%-- <td>${vo.no }</td> --%>
						<td><%= k++ %></td>
						<td><a onclick="location='detail.com?id=${vo.id}&read=1'">${vo.title }
						<!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
						${empty vo.filename ? '' : '<img src="img/image.png" class="file-img"/>' }
						</a></td>
						<td>${vo.writer }</td>
						<td>${vo.writedate }</td>
						<td>${vo.readcnt }</td>
						<td>${vo.likecnt }</td>
					</tr>
					</c:if>
				</c:forEach>			
				</table>
			</div>
		</div>
		<a class="btn-fill" style="float: right;" onclick="location='new.com'">글쓰기</a>
	</div>
	<input type="hidden" name="curPage"/>
</form>
<jsp:include page="/WEB-INF/views/page/page.jsp"/>
<script type="text/javascript">
	function go_page(no){
		$('input[name="curPage"]').val(no);
		$('#list').submit();
	}
	</script>
</body>
</html>