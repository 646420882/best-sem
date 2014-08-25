window.onload = function () {
    rDrag.init(document.getElementById('box'));
	rDrag.init(document.getElementById('box2'));
	rDrag.init(document.getElementById('box3'));
	rDrag.init(document.getElementById('box4'));
    rDrag.init(document.getElementById('box5'));
    rDrag.init(document.getElementById('box6'));
};

$(function(){
//顶部菜单切换
			var $tab_li = $('.tab_menu li');
			$('.tab_menu li').click(function(){
			$(this).addClass('selected').siblings().removeClass('selected');
		    var index = $tab_li.index(this);
		    $('div.tab_box > div').eq(index).show().siblings().hide();
		    });	
//设置规则
			$(".showbox").click(function(){
				$(".TB_overlayBG").css({
					display:"block",height:$(document).height()
				});
				$(".box").css({
					left:($("body").width()-$(".box").width())/2-20+"px",
					top:($(window).height()-$(".box").height())/2+$(window).scrollTop()+"px",
					display:"block"
				});
			});
			$(".close").click(function(){
				$(".TB_overlayBG").css("display","none");
				$(".box ").css("display","none");
			});
//修改出价
$(".showbox2").click(function(){
				$(".TB_overlayBG").css({
					display:"block",height:$(document).height()
				});
				$(".box2").css({
					left:($("body").width()-$(".box2").width())/2-20+"px",
					top:($(window).height()-$(".box2").height())/2+$(window).scrollTop()+"px",
					display:"block"
				});
			});
			$(".close").click(function(){
				$(".TB_overlayBG").css("display","none");
				$(".box2 ").css("display","none");
			});
//修改匹配模式
$(".showbox4").click(function(){
				$(".TB_overlayBG").css({
					display:"block",height:$(document).height()
				});
				$(".box4").css({
					left:($("body").width()-$(".box4").width())/2-20+"px",
					top:($(window).height()-$(".box4").height())/2+$(window).scrollTop()+"px",
					display:"block"
				});
			});
			$(".close").click(function(){
				$(".TB_overlayBG").css("display","none");
				$(".box4 ").css("display","none");
			});
//修改访问网址
$(".showbox3").click(function(){
				$(".TB_overlayBG").css({
					display:"block",height:$(document).height()
				});
				$(".box3").css({
					left:($("body").width()-$(".box3").width())/2-20+"px",
					top:($(window).height()-$(".box3").height())/2+$(window).scrollTop()+"px",
					display:"block"
				});
			});
			$(".close").click(function(){
				$(".TB_overlayBG").css("display","none");
				$(".box3 ").css("display","none");
			});
//设置规则
$(".showbox5").click(function(){
						$(".TB_overlayBG").css({
							display:"block",height:$(document).height()
						});
						$(".box5").css({
							left:($("body").width()-$(".box5").width())/2-20+"px",
							top:($(window).height()-$(".box5").height())/2+$(window).scrollTop()+"px",
							display:"block"
						});
});
			$(".close").click(function(){
				$(".TB_overlayBG").css("display","none");
				$(".box5").css("display","none");
			});
//自定义列
    $(".showbox6").click(function(){
        $(".TB_overlayBG").css({
            display:"block",height:$(document).height()
        });
        $(".box6").css({
            left:($("body").width()-$(".box6").width())/2-20+"px",
            top:($(window).height()-$(".box6").height())/2+$(window).scrollTop()+"px",
            display:"block"
        });
    });
    $(".close").click(function(){
        $(".TB_overlayBG").css("display","none");
        $(".box6").css("display","none");
    });
//弹窗内部切换
			 $(".time_sl").click(function(){
			  $(".time_select").show();
			  $(".time_select01").hide();
			  });
			  $(".time_sl1").click(function(){
			  $(".time_select01").show();
			  $(".time_select").hide();
	     
               });
 //选择框全选
	  
          $("#checkAll").click(function() {
                $('input[name="subbox"]').prop("checked",this.checked);
            });
            var $subbox = $("input[name='subbox']");
            $subbox.click(function(){
                $("#checkAll").prop("checked",$subbox.length == $("input[name='subbox']:checked").length ? true : false);
            });
		 $("#checkAll2").click(function() {
                $('input[name="subbox2"]').prop("checked",this.checked);
            });
            var $subbox2 = $("input[name='subbox2']");
            $subbox2.click(function(){
                $("#checkAll2").prop("checked",$subbox2.length == $("input[name='subbox2']:checked").length ? true : false);
            });
 //高级搜索
			   $(".advanced_search").click(function(){
				   $(".Senior").slideToggle();
                  }); 
	    $(".Screenings").click(function(){
				   $(".Screening_concent ").slideToggle();
      });
 //竞价列表
	    $('.jiangjia_list li').click(function(){
       $(this).addClass('current').siblings().removeClass('current');
        });	
		   $(".short").click(function(){
				   $(".shorts ").slideToggle();
      });
    //设置规则显示隐藏
    $(".right_define").click(function(){
        $(".right_sets1").show();
        $(".right_define1").click(function(){
                $(".right_sets1").hide();
                            });
    });

});


