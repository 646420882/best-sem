<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>大数据智能营销</title>
<link rel="stylesheet" type="text/css" href="public/css/public.css">
<link rel="stylesheet" type="text/css" href="public/css/style.css">
<link rel="stylesheet" type="text/css" href="public/css/login.css">
<script type="text/javascript" src="public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="public/js/tc.min.js"></script>
<script type="text/javascript">
window.onload = function () {
    rDrag.init(document.getElementById('new_riginality2'));
};
$(function () {
    $(".showbox").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#new_riginality").css({
            left: ($("body").width() - $("#new_riginality").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#new_riginality").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box").css("display", "none");
    });

});
</script>
</head>
<body>
	<div class="concent fr over">
		<div class="mid over">
				<div class="on_title over">
					<a href="#">用户中心</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>关联账户</span>
                </div>
				<div id="tab">
						<div class="configure over">
							<div class="configure_top over">
								<h3 class="fl">关联账户</h3>
								<a href="add.jsp" class="fr"> + 添加推广账户</span></a>
							</div>
                            <div class="configure_under over">
                                     <table width="100%" cellspacing="0" border="1">
                                         <thead>
                                            <tr>
                                                <td>&nbsp;<b>已绑定推广账户</b></td>
                                                <td>&nbsp;<b>推广URL</b></td>
                                                <td>&nbsp;<b>Token</b></td>
                                                <td>&nbsp;<b>操作</b></td>
                                              </tr>
                                         </thead>
                                         <tbody>
                                              <tr>
                                                <td>&nbsp;<span class="fl"><img src="public/images/c_logo.jpg"></span><b class="fl">bj-dafengso</b></td>
                                                <td>&nbsp;www.dafengso.cn</td>
                                                <td>&nbsp;<a href="#"></a></td>
                                                <td>&nbsp;<a href="#" class="showbox">同步密码</a></td>
                                              </tr>
                                              <tr>
                                                <td>&nbsp;<span class="fl"><img src="public/images/c_logo.jpg"></span><b class="fl">科技APH搜索</b></td>
                                                <td>&nbsp;www.dafengso.cn</td>
                                                <td>&nbsp;<a href="#">if6aydfjkjk3j34idfndkgnkkkjldkgllkldkgfdkjk23kjkjkjsfwzw</a></td>
                                                <td>&nbsp;<a href="#" class="showbox">同步密码</a></td>
                                              </tr>
                                         </tbody>     
                                        </table>
                            </div>
						</div>
				</div>
		</div>
	</div>
   <!------------------------------->
   <div class="TB_overlayBG" ></div>
   <div class="box" id="new_riginality" style="display:none; width:552px;">
    <h2 id="new_riginality2">同步baidu推广帐号密码-如果您更改了推广帐号密码，请立即同步密码。<a href="#" class="close">关闭</a></h2>
    <div class="mainlist2 over">
            <div class="mainlist">
                <ul>
                    <li>
                        <p>请输入CubeSearch密码，以保证账户安全：</p> 
                        <p><input type="text" class="c_input" ></p>
                    </li>
                     <li>
                        <p>输入推广帐号新密码：</p> 
                        <p><input type="text" class="c_input" ></p>
                    </li>
                     <li>
                        <p>输入推广URL：</p> 
                        <p><input type="text" class="c_input" ></p>
                    </li>
                     <li>
                        <p>输入推广账户Tolen：</p> 
                        <p><input type="text" class="c_input" ></p>
                    </li>
                </ul>
            </div>
            <div class="main_bottom">
                <div class="w_list03">
                    <ul>
                        <li class="current">确认</li>
                    </ul>
                </div>
            </div>
        </div>  
        </div>
</div>
</body>
</html>
