<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/common.css?v=<%= new Date().getTime() %>">
<link href="https://fonts.googleapis.com/css?family=Indie+Flower&display=swap" 
rel="stylesheet">	<!-- 플라워폰트 -->

<style type="text/css">
body {
	text-align: center;
}
ul.tabs {
   margin: 0;
   float: left;
   list-style: none;
   border-left: 1px solid #eee;
   width: 100%;
   font-family: "dotum";
   font-size: 15px;
   height: 50px;
   
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
   margin-left: 450px;
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
<!-- login_info => info_login  -->

<script type="text/javascript">
var tabNo = 1;

   $(function() {

      $(".tab_content").hide(); //모든 탭 숨기기
      $(".tab_content:first").show(); //첫번째 탭 내용 보여주기

      $("ul.tabs li").click(function() {
         $("ul.tabs li").removeClass("active").css("color", "#333");
         //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
         $(this).addClass("active").css("color", "darkred");
         $(".tab_content").hide()

         var activeTab = $(this).attr("rel");
         tabNo = activeTab.substring(3);         
         $('input[name=curPage]').val(1);     
         $("#" + activeTab).fadeIn()

      });   
   });


</script>
<script src="js/bootstrap.js"></script>
</head>
<body marginheight="50px">
   <h3>중고장터</h3>
   <form method="post" action="list.se" id="list">
   <div id="container">
      <!--탭 메뉴 영역  -->
      <div style="width: 100vh;">
      <ul class="tabs">
         <li class="active" rel="tab1">삽니다</li>
         <li rel="tab2">팝니다</li>
       
      <span style="margin-right: -30px;"> 
         <select  name="search" style="height: 28px; width: 92px;">
            <option value="all" ${page.search eq 'all' ? 'selected' : '' }  >전체</option>
            <option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
            <option value="content" ${page.search eq 'content' ? 'selected' : '' }>내용</option>
            <option value="writer" ${page.search eq 'writer' ? 'selected' : '' }>작성자</option>
         </select>
         <input type="text" name="keyword" value="${page.keyword }" style="width:350px;"/> 
            <a class="btn blue" onclick="$('form').submit()">검색</a>   
       </span> 
      </ul>

      
      </div>
      <!--탭 콘텐츠 영역-->
      <div class="tab_container">
         <!--첫번째 탭 내용  -->
         <div id="tab1" class="tab_content">
         	<c:set var="page" value="${buy_page}" scope="request" />
         
            <table id="table" style="width: 100%">
               <tr>
                  <th>번호</th>
                  <th style="width: 450px;">제목</th>
                  <th>작성자</th>
                  <th>작성일</th>
                  <th>조회수</th>
               </tr>
            <c:forEach items="${page.list }" var="vo">
            <c:if test="${vo.code == 1 }">
            
               <tr>                 
               <td>${vo.no }</td>          
				
				 <c:if test="${ !empty info_login}">
				<td><a onclick="location='detail.se?id=${vo.id}&read=1'">${vo.title }</a></td> 
        	    </c:if>    	      
                 
                 <c:if test="${ empty info_login}">
				 <td><a onclick="location='login.ho'">${vo.title }</a></td>
        	    </c:if>  
        	    
                   <!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
                  <td>${vo.writer }</td>
                  <td>${vo.writedate }</td>
                  <td>${vo.readcnt }</td>
               </tr>
               </c:if>
            </c:forEach>            
            </table>
<%--             <jsp:include page="/WEB-INF/views/include/page.jsp" /> --%>
         </div>
         
         <!-- 두번째 탭 내용 -->
         <div id="tab2" class="tab_content">
          	<c:set var="page" value="${sell_page}" />
         
            <table id="table" style="width: 100%">
               <tr>
                  <th>번호</th>
                  <th style="width: 450px;">제목</th>
                  <th>작성자</th>
                  <th>작성일</th>
                  <th>첨부파일</th>
                  <th>조회수</th>
               </tr>
               <c:forEach items="${page.list }" var="vo">
               <c:if test="${vo.code == 2 }">      
               <tr>
                  <td>${vo.no }</td>
    	                      
                 <c:if test="${ empty info_login}">
				 <td><a onclick="location='login.ho'">${vo.title }</a></td>
        	    </c:if>  
                  
				<c:if test="${ !empty info_login}">
				<td><a onclick="location='detail.se?id=${vo.id}&read=1'">${vo.title }   	 	
                  <!--첨부파일 있는 경우에만 다운로드 이미지 나타내기-->
                  ${empty vo.filename ? '' : '<img src="img/image.png" class="file-img"/>' }
                  </a></td>
                   </c:if>
                  <td>${vo.writer }</td>
                  <td>${vo.writedate }</td>
                  <td>${vo.filename }</td>
                  <td>${vo.readcnt }</td>
               </tr>
               </c:if>
            </c:forEach>         
            </table>
<%-- 		  	<jsp:include page="/WEB-INF/views/include/page.jsp" /> --%>
         </div>
      </div>
     <c:if test="${ !empty info_login }">
	<a class="btn blue" style="float:right;" onclick="location='new.se'">글쓰기</a>
	</c:if>
   </div>
   <input type="hidden" name="curPage"/>
   <input type="hidden" name="code"/>
</form>
<script type="text/javascript">
   function go_page(no){
      $('input[name=curPage]').val(no);
      $('input[name=code]').val(tabNo);
      $('#list').submit();
   }
   </script>
</body>
</html> 