<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-7-25
  Time: 下午6:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">

    <style type="text/css">
        .title{
            width:100%;
            height:60px;
            background-color: #ffffff;
            border-bottom: 2px solid #CBC5D1;
        }

        .title span{
            float:left;
            margin-left: 15px;
            margin-top: 20px;
            font-size:16px;
            font-family: "微软雅黑";
            height: 67%;
            border-bottom: 2px solid gray;
        }

    </style>

</head>
<body>
<div class="concent over">
    <div class="mid over fr">
        <div class="on_title over">
            <a href="#">
                推广助手
            </a>
            &nbsp;&nbsp;>&nbsp;&nbsp;<span>账户预警</span>
        </div>

        <div class="title"><span>消费提醒</span></div>
        <div><br/>
            <table id = "table" border="1">
                <thead>
                    <tr>
                        <td>百度账户ID</td>
                        <td>预算类型</td>
                        <td>预算金额</td>
                        <td>预警百分率</td>
                        <td>手机号码</td>
                        <td>邮箱地址</td>
                        <td>是否启用</td>
                    </tr>
                </thead>
                <tbody id = "warningTr">

                </tbody>
            </table>

        </div>





        <div class="footer">
            <p>
                <a href="#">
                    关于我们
                </a>
                |
                <a href="#">
                    联系我们
                </a>
                |
                <a href="#">
                    隐私条款
                </a>
                |
                <a href="#">
                    诚聘英才
                </a>
            </p>

            <p>
                Copyright@2013 perfect-cn.cn All Copyright Reserved. 版权所有：北京普菲特广告有限公司京ICP备***号
            </p>
        </div>
    </div>
</div>


<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
    /**
    *得到预警规则信息
     */
    function getWarningRuleData(){
        $.ajax({
            url:"/assistant/getAllWarningRule",
            type:"POST",
            data:{},
            dataType:"json",
            success:function(data){
                for(var i=0;i<data.length;i++){
                    var html = "";
                    html = html+"<tr><td>"+data[i].accountId+"</td>";

                    if(data[i].budgetType==0){
                        html = html+"<td>不设置预算</td>";
                    }else if(data[i].budgetType==1){
                        html = html+"<td>日预算</td>";
                    }else{
                        html = html+"<td>周预算</td>";
                    }

                    html = html+"<td>"+data[i].budget+"</td>";
                    html = html+"<td>"+data[i].warningPercent+"%</td>";
                    html = html+"<td>"+data[i].tels+"</td>";
                    html = html+"<td>"+data[i].mails+"</td>";

                    if(data[i].isEnable==0){
                        html = html+"<td><select class='enbled' name='"+data[i].id+"'><option value = '0' selected='true'>禁用</option> <option value = '1'>启用</option></select></td></tr>";
                    }else{
                        html = html+"<td><select class='enbled' name='"+data[i].id+"'><option value = '0'>禁用</option> <option value = '1'  selected='true'>启用</option></select></td></tr>";
                    }
                    $("#warningTr").append(html);
                }
                $("#table").append("<a href='/assistant/accountWarning'>去设置预警</a>");
            }

        });
    }
    getWarningRuleData();



    /**
    *修改启用或者禁用
    * @param id
    * @param value
     */

    $("#table").delegate(".enbled","change",function(){
        $.ajax({
         url:"/assistant/updateWarningRuleOfIsEnbled",
         type:"post",
         data:{"id":$(this).attr("name"),"isEnbled":$(this).val()}
         });
    });

</script>

</body>
</html>