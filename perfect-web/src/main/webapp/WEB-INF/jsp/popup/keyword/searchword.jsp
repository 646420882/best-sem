<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2014/9/3
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <style type="text/css">
        .k_l_under{
            text-align: center;
            line-height:24px;
        }
        .searchword_text textarea{
            width:100%;
            height:140px;
            overflow: auto;

        }
       .k_l_top {
            margin: 0px 2% 10px;
        }
        .k_l_under{
            margin: 0px 0 10px;
        }
        .become{
            margin:20px 15px 0 0 ;
        }
    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 800px">
    <div class="searchword_top over">
        <ul>
            <li>
                <span>报告分析对象</span>
               <select>
                   <option value="11">关键词</option>
                   <option value="7">普通创意</option>
                   <option value="5">推广单元</option>
                   <option value="3">推广计划</option>
                   <option value="2">账户</option>
               </select>
            </li>
            <li>
                <span>时间范围</span>
                <select>
                    <option value="1">昨天</option>
                    <option value="7">最近七天</option>
                    <option value="30">最近一月</option>
                </select>
            </li>
            <li>
                <span>地域范围</span>
                <a href="#">全部地域</a>
                <input type="image" src="../public/img/zs_input.png">
            </li>
            <li>
                <span>投放设备</span>
               <select>
                   <option value="0">全部设备</option>
                   <option value="1">计算机设备</option>
                   <option value="2">移动设备</option>
               </select>
            </li>
            <li>
                <span>搜索引擎范围</span>
               <select>
                   <option>全部</option>
                   <option>百度推广</option>
                   <option>非百度推广</option>
               </select>
            </li>
        </ul>
        <a class="become fr" href="javascript:"> 生成报告</a>
    </div>
    <div class="searchword_under over">
        <div class="searchword_under1 fl over">
                <div class="search_input over">
                    <div class="k_l_top over">
                        <span>已选关键词（1/500）</span>
                    </div>
                    <div class="searchword_text over">
                        <textarea></textarea>
                    </div>
                    <div class="k_l_under over">
                        <a href="#">保存至账户</a>
                    </div>
                </div>
            <div class="search_input over">
                <div class="k_l_top over">
                    <span>已选否定词（1/500）</span>
                </div>
                <div class="searchword_text over">
                    <textarea></textarea>
                </div>
                <div class="k_l_under over">
                    <a href="#">设置至账户</a>
                </div>

            </div>
        </div>
        <div class="searchword_under2 fr over">
            <div class="search_mid over">
                  <span>请于上方确定报告对象及范围后再生成报告</span>
            </div>
            <input type="button" value="导出全部到文件" class="zs_input2">
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
</html>
