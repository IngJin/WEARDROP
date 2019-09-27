
function go_logout() {
	$.ajax({
		url : 'logout_log',
		success: function() {
			location='index';
		}, error: function(req, text) {
			alert( txt + ": " + req.status);
		}
	});
}

$(function(){

    var group = $(".group");

    group.each(function(){
        var _group = new GroupBox(this);
    });

    // 사용자 정의 생성자 함수 정의
    function GroupBox(groupElement){

        var box = $(groupElement).find(".box");
        var title = $(groupElement).find(".box .title a");

        box.each(function(idx){
            var newBox = new RootBox(this);
            if (idx >= 0){
                newBox.siblingsClose();
            }
        });
    }

    // 사용자 정의 생성자 함수 정의
    function RootBox(boxElement){
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

        _this.siblingsClose = function () {
            cont.css('display','none');
        }

        _this.open = function() {
            cont.slideDown();
        }
        _this.close = function() {
            cont.slideUp();
        }
    }
});

$(function($) {
    $("body").css("display", "none");
    $("body").fadeIn(1000);
    $("a.transition").click(function(event){
        event.preventDefault();
        linkLocation = this.href;
        $("body").fadeOut(500, redirectPage);
    });
    function redirectPage() {
    window.location = linkLocation;
    }
});

$(function(){
	   // gnb
	   $('#header a.all-menu').on('click',function(){
	      if ($(this).hasClass('active')) {
	         $('.mask').remove();
	         $('body').removeClass('ofH');
	         $('.gnbwrap').removeClass('open');
	         $('.header .fixmenu').removeClass('active');
	         $(this).removeClass('active');
	      } else {
	         $('.gnbwrap').addClass('open');
	         $('body').addClass('ofH');
	         $('.header .fixmenu').addClass('active');
	         $(this).addClass('active');
	         $('body').append('<div class="mask"></div>');
	         $('.mask').css({'z-index':500});
	      }
	      return false;
	   });
});