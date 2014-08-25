<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/7/24
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="nav fl">
    <div class="nav_left over fl">
        <div class="user over">
            <div class="user_img fl">
							<span class="img">
								<img src="${pageContext.request.contextPath}/public/img/user.png">
							</span><span class="name"> JOHN DOE </span>
            </div>
            <div class="user_close fr">
                <a href="/logout">
                    退出
                </a>
            </div>
        </div>
        <div class="nav_mid">
            <a href="/home"><div class="nav_top">
             帐户全景<span></span>
            </div></a>
            <div class="nav_under over">
                <ul>
                    <a href="/assistant/index">
                        <li>
                            <h3>推广助手</h3>
                        </li>
                    </a>
                    <li>
                        <span class="list1"></span>
                        <a href="#">
                            账户预警
                        </a>
                    </li>
                    <li>
                        <span class="list2"></span>
                        <a href="/upload/uploadManager">
                            批量上传
                        </a>
                    </li>
                    <li>
                        <span class="list3"></span>
                        <a href="/upload/uploadTotal">
                            批量操作
                        </a>
                    </li>
                    <li>
                        <span class="list4"></span>
                        <a href="#">
                            关键词查找
                        </a>
                    </li>
                    <li>
                        <span class="list5"></span>
                        <a href="#">
                            推广查询
                        </a>
                    </li>
                    <li>
                        <h3>智能结构</h3>
                    </li>
                    <li>
                        <span class="list6"></span>
                        <a href="#">
                            关键词拓展
                        </a>
                    </li>
                    <li>
                        <span class="list7"></span>
                        <a href="/keyword_group">
                            智能分组
                        </a>
                    </li>
                    <li>
                        <h3><a href="/bidding/index">智能竞价</a></h3>
                    </li>
                    <li>
                        <span class="list8"></span>
                        <a href="#">
                            诊断关键词
                        </a>
                    </li>
                    <li>
                        <span class="list9"></span>
                        <a href="#">
                            筛选关键词
                        </a>
                    </li>
                    <li>
                        <span class="list10"></span>
                        <a href="#">
                            设置规则
                        </a>
                    </li>
                    <li>
                        <h3>数据报告</h3>
                    </li>
                    <li>
                        <span class="list11"></span>
                        <a href="/reportIndex">
                            基础报告
                        </a>
                    </li>
                    <li>
                        <span class="list12"></span>
                        <a href="#">
                            定制报告
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tips fl">
        <input type="image" src="${pageContext.request.contextPath}/public/img/button2.png">
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        var navH = $(".on_title").offset().top;
        $(window).scroll(function(){
            var scroH = $(this).scrollTop();
            if(scroH>=navH){
                $(".on_title").css({"position":"fixed","top":"77"});
            }else{
                $(".on_title").css({"position":"static","margin":"0 auto"});
            }
        });
    });

</script>
