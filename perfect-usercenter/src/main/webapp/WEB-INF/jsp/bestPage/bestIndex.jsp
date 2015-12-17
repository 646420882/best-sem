<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-12-9
  Time: 下午7:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>欢迎来到百思</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/public.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/shun_page.css">
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script>
        var _pct = _pct || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//t.best-ad.cn/t.js?tid=76c005e89e020c6e8813a5adaba384d7";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>
<div class="mid wd">
    <div class="logo">
        <a href="http://best-ad.cn/" target="_blank"><img
                src="${pageContext.request.contextPath}/public/img/best_img/logo.png"></a>
    </div>
    <div class="servicesBox">
        <div class="time over">
            <b id="time" class="fl"></b><a href="/userCenter/index"><span class="fl">${user.userName}</span></a>

            <form class="fl" name="logout" method="POST" action="/logout">
                <input type="submit" value="| 退出"
                       onclick="$('form[logout]').submit();"/>
            </form>
        </div>

        <div class="serBox disabled">
            <div class="serBoxOn"></div>
            <div class="pic1"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_sutou.png"></div>
            <div class="pic2"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_sutou2.png"></div>
            <div class="txt1"><span class="tit">百思速投</span>

                <p>针对搜索引擎营销的</p>

                <p>第三方优化管理平台</p>
            </div>
            <div class="txt2">
                <a href="javascript:void(0);" onclick="js_method()" class="a_jump"><span class="tit">百思速投</span></a>

                <div class="tex2_mid">
                    <p>针对搜索引擎营销的</p>

                    <p>第三方优化管理平台</p>
                </div>
                <div class="enter">
                    <a href="javascript:void(0);" onclick="js_method()">敬请期待</a>
                </div>
            </div>
            <div>
            </div>
        </div>
        <div class="serBox" onclick="window.open('http://192.168.1.190:8081/token?tokenid=${userToken}')">
            <div class="serBoxOn"></div>
            <div class="pic1"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_shouke.png"></div>
            <div class="pic2"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_shouke2.png"></div>
            <div class="txt1"><span class="tit">百思搜客</span>

                <p>针对搜索引擎营销的</p>

                <p>第三方优化管理平台</p></div>
            <div class="txt2"><a href="javascript:void(0);" class="a_jump"><span
                    class="tit">百思搜客</span></a>

                <div class="tex2_mid">
                    <p>推广额度：<b> ${accountBalance} </b>元</p>

                    <p>余额预计可消费<b> ${remainderDays} </b>天</p>

                    <p>日预算<b> ${accountBudget} </b>元</p>
                </div>
                <div class="enter">
                    <a href="javascript:void(0);">点击进入</a>
                </div>
            </div>
        </div>
        <div class="serBox" <%--onclick="window.open('http://seo.best-ad.cn/auth/login?username=${currSystemUserName}')"--%>>
            <div class="serBoxOn"></div>
            <div class="pic1"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_seo.png"></div>
            <div class="pic2"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_seo2.png"></div>
            <div class="txt1"><span class="tit">百思SEO</span>

                <p>SEO追踪，集统计、分析和管理</p>

                <p>专家级的建议和优化方案</p>

                <p></p>
            </div>
            <div class="txt2"><a href="javascript:void(0);" onclick="js_method()" class="a_jump"><span
                    class="tit">百思SEO</span></a>

                <div class="tex2_mid">
                    <p>SEO追踪，集统计、分析和管理</p>

                    <p>专家级的建议和优化方案</p>

                    <p></p>
                </div>
                <div class="enter">
                    <a href="javascript:void(0);">点击进入</a>
                </div>
            </div>
        </div>
        <div class="serBox disabled">
            <div class="serBoxOn"></div>
            <div class="pic1"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_wuxian.png"></div>
            <div class="pic2"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_wuxian2.png"></div>
            <div class="txt1">
                <span class="tit">百思无线</span>

                <p>一站式解决客户移动建站</p>

                <p>及微信运营推广</p>
            </div>
            <div class="txt2"><a href="javascript:void(0);" onclick="js_method()" class="a_jump"><span
                    class="tit">百思无线</span></a>

                <div class="tex2_mid">
                    <p>一站式解决客户移动建站 </p>

                    <p>及微信运营推广</p>
                </div>
                <div class="enter">
                    <a href="javascript:void(0);" onclick="js_method()">敬请期待</a>
                </div>
            </div>
        </div>
        <div class="serBox" onclick="window.open('http://seo.best-ad.cn/auth/login?username=${currSystemUserName}')">
            <div class="serBoxOn"></div>
            <div class="pic1"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_huiyan.png"></div>
            <div class="pic2"><img src="${pageContext.request.contextPath}/public/img/best_img/shunt_huiyan2.png"></div>
            <div class="txt1">
                <span class="tit">百思慧眼</span>

                <p>云端备份数据</p>

                <p>快速实现信息同步</p>

                <p></p>
            </div>
            <div class="txt2"><a href="javascript:void(0);" onclick="js_method()" class="a_jump"><span
                    class="tit">百思慧眼</span></a>

                <div class="tex2_mid">
                    <p>云端备份数据</p>

                    <p>快速实现信息同步</p>

                    <p></p>
                </div>
                <div class="enter">
                    <a href="javascript:void(0);">点击进入</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer wd over">
    <a href="http://www.perfect-cn.cn/" target="_blank">普菲特官网 </a>如在使用过程中有任何问题请联系客服：010-84922996
    <script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F9e0d99624d24d17bb95abc5ca469c64e' type='text/javascript'%3E%3C/script%3E"));
    </script>
</div>
<script type="text/javascript">
    $(".serBox").hover(function () {
        $(this).children().stop(false, true);
        $(this).children(".serBoxOn").fadeIn("slow");
        $(this).children(".pic1").animate({right: -220}, 400);
        $(this).children(".pic2").animate({left: 50}, 400);
        $(this).children(".txt1").animate({left: -220}, 400);
        $(this).children(".txt2").animate({right: 0}, 400);
        $(this).attr("style", "height:338px;*height:334px;background:#01aeef");

    }, function () {
        $(this).children().stop(false, true);
        $(this).children(".serBoxOn").fadeOut("slow");
        $(this).children(".pic1").animate({right: 45}, 400);
        $(this).children(".pic2").animate({left: -220}, 400);
        $(this).children(".txt1").animate({left: 0}, 400);
        $(this).children(".txt2").animate({right: -220}, 400);
        $(this).removeAttr("style");
    });
    $(".disabled").hover(function () {
        $(this).attr("style", "height:306px;background:#01aeef");
    }, function () {
        $(this).removeAttr("style");

    });

    var now = new Date(), hour = now.getHours();
    var time = document.getElementById('time');
    if (0 < hour && hour < 6) {
        time.innerHTML = "凌晨,好！"
    }
    else if (hour < 9) {
        time.innerHTML = "早上,好！"
    }
    else if (hour < 12) {
        time.innerHTML = "上午,好！"
    }
    else if (hour < 14) {
        time.innerHTML = "中午,好！"
    }
    else if (hour < 18) {
        time.innerHTML = "下午,好！"
    }
    else if (hour < 23) {
        time.innerHTML = "晚上,好！"
    }
    else if (hour == 24) {
        time.innerHTML = "凌晨,好！"
    }
    else {
        time.innerHTML = "晚上,好！"
    }
</script>

</body>
</html>
