<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script>

</script>
<table id="table" style="width: 100%">
	<tr>
		<th>번호</th>
		<th style="width: 450px;">제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${page.list }" var="vo">

			<tr>
				<td>${vo.no }</td>

				<c:if test="${ !empty info_login}">
					<td><a onclick="location='detail.se?id=${vo.id}&code=${vo.code}&read=1'">${vo.title }
						<!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
						${empty vo.filename ? '' : '<img src="img/icon/gal_image.png" class="gal_icon" style="margin-left:3px;"/>' }
						
						<c:if test="${!empty salcomp.trade}">
						${empty vo.filename ? '' : '<img src="img/icon/sell_image.png" class="sell_icon" style="margin-left:3px;"/>' }
						</span> 
					</a></td>
				
				</c:if>

				<c:if test="${ empty info_login}">
					<td><a onclick="location='login.ho'">${vo.title }</a></td>
				</c:if>
				
				</c:if>
				
				<!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
				<td>${vo.writer }</td>
				<td>${vo.writedate }</td>
				<td>${vo.readcnt }</td>
			</tr>
	</c:forEach>
</table>
<%-- <jsp:include page="/WEB-INF/views/include/page.jsp" /> --%>