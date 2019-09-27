$(function($) {
    $("body").css("display", "none");
    $("body").fadeIn(800);
});

function we_nothover() {
    $("#display").fadeOut(0.1);
    $("#display").css("display", "none");
    $("#display").empty();
}

function nothover() {
    we_nothover();
    $("#myVideo").attr("src", "video/main.mp4");
    $(".display").css('background-image', '');
    $(".we_img").css("background-image", '');
}

function notmain() {
    $("#myVideo").attr("src", "video/main.mp4");
}

function hot() {
    $(".display").css("display", "none");
    $(".display").css('background-image', 'url("img/weather_hot.jpg")');
    $(".display").fadeIn(800);
}

function cold() {
    $(".display").css("display", "none");
    $(".display").css('background-image', 'url("img/weather_cold.jpg")');
    $(".display").fadeIn(800);
}

var ip = "https://jsonip.com";
$.ajax({
    url: ip,
    type: "post",
    dataType: "json",
    async: "false",
    success: function(ip) {
        var ipURI = "http://ip-api.com/json/" + ip.ip + "?fields=country,region,regionName,lat,lon,query";
        $.ajax({
            url: ipURI,
            type: "post",
            dataType: "json",
            async: "false",
            success: function(resp) {
                var apiURI = "";
                apiURI += "https://api.openweathermap.org/data/2.5/weather?lat=" +
                    resp.lat + "&lon=" + resp.lon +
                    "&appid=e5799cbefe2fe84bbfe2c2936d4816f4";
                $.ajax({
                    url: apiURI,
                    dataType: "json",
                    type: "GET",
                    async: "false",
                    success: function(resp) {
                        $('#daliy').hover(function() {
                            we_nothover();
                            $("#myVideo").css("display", "none");
                            $("#myVideo").attr("src", "video/daliy.mp4");
                            $("#myVideo").fadeIn(800);
                            $(".dailyLook_co").css("display", "none");
                            $(".dailyLook_co").fadeIn(800);
                        }, function() {
                            $(".dailyLook_co").fadeOut(0.1);
                            $(".dailyLook_co").css("display", "none");
                            notmain();
                        });

                        $('#market').hover(function() {
                            we_nothover();
                            $("#myVideo").css("display", "none");
                            $("#myVideo").attr("src", "video/usermarket.mp4");
                            $("#myVideo").fadeIn(800);
                            $(".usermarket_co").css("display", "none");
                            $(".usermarket_co").fadeIn(800);
                        }, function() {
                            $(".usermarket_co").fadeOut(0.1);
                            $(".usermarket_co").css("display", "none");
                            notmain();
                        });

                        $('#service').hover(function() {
                            we_nothover();
                            $("#myVideo").css("display", "none");
                            $("#myVideo").attr("src", "video/service.mp4");
                            $("#myVideo").fadeIn(800);
                            $(".service_co").css("display", "none");
                            $(".service_co").fadeIn(800);
                        }, function() {
                            $(".service_co").fadeOut(0.1);
                            $(".service_co").css("display", "none");
                            notmain();
                        });

                        $('#community').hover(function() {
                            we_nothover();
                            $("#myVideo").css("display", "none");
                            $("#myVideo").attr("src", "video/comm.mp4");
                            $("#myVideo").fadeIn(800);
                            $(".community_co").css("display", "none");
                            $(".community_co").fadeIn(800);
                        }, function() {
                            $(".community_co").fadeOut(0.1);
                            $(".community_co").css("display", "none");
                            notmain();
                        });

                        $('#weather').hover(function() {
                            if ((resp.weather[0].main).toString() == "Rain" || (resp.weather[0].main).toString() == "Drizzle" || (resp.weather[0].main).toString() == "Thunderstorm") {
                                weather();
                                $("#myVideo").css("display", "none");
                                $("#myVideo").attr("src", "video/rain.mp4");
                                $("#myVideo").fadeIn(800);
                                if ((resp.main.temp - 273.15) > 18) {
                                    hot();
                                } else {
                                    cold();
                                }
                            } else if ((resp.weather[0].main).toString() == "Snow") {
                                weather();
                                $("#myVideo").css("display", "none");
                                $("#myVideo").attr("src", "video/show.mp4");
                                $("#myVideo").fadeIn(800);
                                if ((resp.main.temp - 273.15) > 18) {
                                    hot();
                                } else {
                                    cold();
                                }
                            } else if ((resp.weather[0].main).toString() == "Clear") {
                                weather();
                                $("#myVideo").css("display", "none");
                                $("#myVideo").attr("src", "video/clear.mp4");
                                $("#myVideo").fadeIn(800);
                                if ((resp.main.temp - 273.15) > 18) {
                                    hot();
                                } else {
                                    cold();
                                }
                            } else if ((resp.weather[0].main).toString() == "Clouds") {
                                weather();
                                $("#myVideo").css("display", "none");
                                $("#myVideo").attr("src", "video/cloud.mp4");
                                $("#myVideo").fadeIn(800);
                                if ((resp.main.temp - 273.15) > 18) {
                                    hot();
                                } else {
                                    cold();
                                }
                            } else {
                                weather();
                                $("#myVideo").css("display", "none");
                                $("#myVideo").attr("src", "video/mist.mp4");
                                $("#myVideo").fadeIn(800);
                                if ((resp.main.temp - 273.15) > 18) {
                                    hot();
                                } else {
                                    cold();
                                }
                            }
                        }, function() {
                            nothover();
                        });
                    }
                });
            }
        });
    }
});


function weather() {
    var ip = "https://jsonip.com";
    $.ajax({
        url: ip,
        type: "post",
        dataType: "json",
        async: "false",
        success: function(ip) {
            var ipURI = "http://ip-api.com/json/" + ip.ip + "?fields=country,region,regionName,lat,lon,query";
            $.ajax({
                url: ipURI,
                type: "post",
                dataType: "json",
                async: "false",
                success: function(resp) {
                    var apiURI = "";
                    apiURI += "https://api.openweathermap.org/data/2.5/weather?lat=" +
                        resp.lat + "&lon=" + resp.lon +
                        "&appid=e5799cbefe2fe84bbfe2c2936d4816f4";
                    $.ajax({
                        url: apiURI,
                        dataType: "json",
                        type: "GET",
                        async: "false",
                        success: function(resp) {
                            $('#weather').hover(function() {
                                $("#display").empty();
                                var html = ""
                                html += "<div class='temp'>" + (resp.main.temp - 273.15) + "˚</div>";
                                html += "<div class='wet'>" + resp.weather[0].main + "</div>";
                                if ((resp.weather[0].main).toString() == "Rain" || (resp.weather[0].main).toString() == "Drizzle" || (resp.weather[0].main).toString() == "Thunderstorm") {
                                    $(".we_img").css("background-image", 'url("img/we_rain.png")');
                                } else if ((resp.weather[0].main).toString() == "Snow") {
                                    $(".we_img").css("background-image", 'url("img/we_show.png")');
                                } else if ((resp.weather[0].main).toString() == "Clear") {
                                    $(".we_img").css("background-image", 'url("img/we_sun.png")');
                                } else if ((resp.weather[0].main).toString() == "Clouds") {
                                    $(".we_img").css("background-image", 'url("img/we_cloud.png")');
                                } else {
                                    $(".we_img").css("background-image", 'url("img/we_mist.png")');
                                }

                                if ((resp.main.temp - 273.15) >= 27) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>나시티, 반바지, 민소매, 원피스</div>"
                                } else if ((resp.main.temp - 273.15) >= 23) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>반팔, 얇은 셔츠, 얇은 긴팔, 반바지, 면바지</div>"
                                } else if ((resp.main.temp - 273.15) >= 20) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>긴팔티, 가디건, 후드티, 면바지, 슬렉스, 스키니</div>"
                                } else if ((resp.main.temp - 273.15) >= 17) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스</div>"
                                } else if ((resp.main.temp - 273.15) >= 12) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>자켓, 셔츠, 가디건, 간절기 야상, 살색스타킹</div>"
                                } else if ((resp.main.temp - 273.15) >= 10) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>트렌치코트, 간절기 야상, 여러겹 껴입기</div>"
                                } else if ((resp.main.temp - 273.15) >= 6) {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>코트, 가죽자켓</div>"
                                } else {
                                    html += "<div class='tocody'>Today is Cody</div><div class='tocodys'>겨울 옷(야상, 패딩, 목도리 등등 다)</div>"
                                }
                                $("#display").css("display", "none");
                                $("#display").append(html);
                                $("#display").fadeIn(800);
                                $(".we_img").css("display", "none");
                                $(".we_img").fadeIn(800);
                            }, function() {
                                nothover();
                            });
                        }
                    })
                }
            });
        }
    });
}

$(function() {
    var group = $(".group");
    group.each(function() {
        var _group = new GroupBox(this);
    });

    // 사용자 정의 생성자 함수 정의
    function GroupBox(groupElement) {
        var box = $(groupElement).find(".box");
        var title = $(groupElement).find(".box .title a");

        box.each(function(idx) {
            var newBox = new RootBox(this);
            if (idx >= 0) {
                newBox.siblingsClose();
            }
        });
    }

    // 사용자 정의 생성자 함수 정의
    function RootBox(boxElement) {
        var _this = this;
        var boxEl = $(boxElement);
        var target = $(boxEl).find(".title a");
        var cont = $(boxEl).find(".cont");

        // _groupParent = $(boxEl).parent();
        target.on("click", anchorClickEvent);

        function anchorClickEvent() {
            if (cont.is(':hidden')) {
                _this.open();
            } else {
                _this.close();
            }
        }
        _this.siblingsClose = function() {
            cont.css('display', 'none');
        }
        _this.open = function() {
            cont.slideDown();
        }
        _this.close = function() {
            cont.slideUp();
        }
    }
});

function go_logout() {
    $.ajax({
        url: 'logout_log',
        success: function() {
            location = 'index';
        },
        error: function(req, text) {
            alert(txt + ": " + req.status);
        }
    });
}