<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>		<!--jquery 선언-->
<script src="js/shuffle-text/build/shuffle-text.js"></script>		<!-- jQuery 설정 -->
<style type="text/css">
body {
text-align: center;
}
#columns {
   colum-count:3;
   /* column-width: 250px;
   column-gap: 15px; */
}

#columns figure {
   display: inline-block;
   border: 1px solid rgba(0, 0, 0, 0.2);
   margin: 0;
   margin-bottom: 15px;
   padding: 10px; 
   box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
}

#columns figure img {
    width: 300px;
   	height: 350px; 
}

#columns figure figcaption {
   border-top: 1px solid rgba(0, 0, 0, 0.2);
   padding: 10px;
   margin-top: 11px;
}
</style>
</head>
<body>
	<h3>후기게시판</h3>
		<form action="list.hu" method="post" id="list">
		<!--검색 select메뉴  -->
		<span class="search" style="margin-right: 130px;"> 
		<select name="search" style="height: 28px; width: 92px;">
				<option value="all" ${search eq 'all' ? 'selected' : '' }>전체</option>
				<option value="title" ${search eq 'title' ? 'selected' : '' }>제목</option>
				<option value="writer" ${search eq 'writer' ? 'selected' : '' }>작성자</option>
		</select> 
		<input type="text" name="keyword" value="${keyword }" style="width: 350px;" /> 
		<a class="btn-fill" onclick="$('form').submit()">검색</a>
		</span>
		
		
			<a onclick="location='new.hu'">글쓰기</a>
		
		
		<!--본문  -->
	 <div id="columns">
		<c:forEach items="${list }" var="vo">
         <figure>
               <c:if test="${ empty vo.filepath}">
		            <img src="img/logo.png" onclick="location='detail.hu?id=${vo.id}&read=1'">		
            </c:if>
             <c:if test="${ !empty vo.filepath}">
		        <img src="<c:url value='/'/>${vo.filepath}" onclick="location='detail.hu?id=${vo.id}&read=1'">
		    </c:if>
            <figcaption>
            	<span>${vo.title}</span>
            	<div>
            		<span>${vo.writer}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            		<span>${vo.writedate }</span>
            	</div>
            </figcaption>
         </figure>
   </c:forEach>
   </div>
	</form>
</body>
</html>