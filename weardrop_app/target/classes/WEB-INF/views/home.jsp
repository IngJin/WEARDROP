<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<title>WEARDROP</title>
<head>
    <title>home</title>
    <link rel="stylesheet" type="text/css" href="css/common.css?v=<%= new Date().getTime() %>">
    <link rel="icon" type="image/x-icon" href="img/hanul.ico">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="js/home.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Darker+Grotesque&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Black+Han+Sans&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Orbitron&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Gugi&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <script type="text/javascript" src="js/common.js"></script>
    <link rel="icon" type="image/x-icon" href="img/icon.png">
    <link rel="stylesheet" type="text/css" href="css/home.css?v=<%= new Date().getTime() %>">
</head>

<body>
    <video autoplay muted loop id="myVideo">
        <source src="video/main.mp4" type="video/mp4">
    </video>
    <div class="out">
        <div class="content">
            <div id="content">
                <a onclick="location='index'"><img src="img/logo.png" alt="로고" style=" width: 280px; height: auto" /></a><br /><br /><br />
                <a id="weather">What should I wear Today?</a> <br /><br />
                <a id="daliy" href="list.gal">DailyLook</a> <br /><br />
                <a id="market" href="list.se">UsedMarket</a><br /><br />
                <div class="group">
                    <div class="box">
                        <div id="service" class="title"><a href="#">Service Center</a></div>
                        <div class="cont"><a href="list.no">Notice</a></div>
                        <div class="cont" id="size"><a href="list.ct">Questions and Answer</a></div>
                    </div>

                    <div class="box">
                        <div id="community" class="title"><a href="#">Community</a></div>
                        <div class="cont"><a href="list.com">Free Board</a></div>
                        <div class="cont"><a href="list.hu">Reviews</a></div>
                        <div class="cont"><a href="map.com">Find a store</a></div>
                    </div><br />
                </div>
                </br>
                <c:if test="${ !empty info_login}">
                    <div class="user_info"><a onclick="location='mypage.ho'">${info_login.userid} [${info_login.writer}]</a></div><br />
                    <a class="btn" onclick="go_logout()">Logout</a>                    
                    <c:if test="${info_login.admin eq 'Y'}">
                   	<a class="manager" onclick="location='manager.list'">관리자</a>
                    </c:if>
                </c:if>
                <c:if test="${ empty info_login}">
                    <a class="btn" onclick="location='login.ho'">Login</a>
                </c:if>
            </div>
        </div>
    </div>
    <div class="display">
        <div class='we_img'></div>
        <div id="display"></div> <br />
        <div class="weather"></div>
        <div class="dailyLook_co">당신을 위한 코디</div>
        <div class="usermarket_co">믿을 수 있는 중고거래</div>
        <div class="service_co">고객 만족을 위한 서비스</div>
        <div class="community_co">뱉은 말은 주어담을 수 없습니다.</div>
    </div>
</body>

</html>