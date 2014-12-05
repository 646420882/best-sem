<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/12/1
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>统计页面配置</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="/public/css/count/count.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <script>
        $(function(){

            var url = "";
            loadGrid();
            var  ipset = ((returnCitySN["cip"] == undefined) ? "" : returnCitySN["cip"]);
            $("form input[type='button']").click(function(){
                var _val=$("#url").val();
                var _urlDesc=$("#urlDesc").val();
                var _trSize=$("table tbody tr").length;
                if(_trSize>=4){
                    alert("目前只支持4个统计地址");
                    return;
                }
                if(_val==""){
                    alert("请输入要统计的Url地址..");
                    return;
                }if(!IsURL(_val)){
                    alert("请输入正确的Url地址");
                    return;
                }

                var conf=confirm("你确定要添加这个Url地址为作为监控页面？");
                if(conf){
                    $.post("/pftstis/saveUrl",{url:_val,ip:ipset,urlDesc:_urlDesc},function(res){
                        if(res.result=="1"){
                            loadGrid();
                        }if(res.result=="0"){
                            alert("已经添加该Url地址了");
                        }
                    });
                }
            });
        });

        function loadGrid(){
            var  ipset = ((returnCitySN["cip"] == undefined) ? "" : returnCitySN["cip"]);
            $.post("/pftstis/getCfgList",{ip:ipset},function(res){
                var _table = $("table");
                var _list = res.rows;
                _table.find("tbody").empty();
                for (var i = 0; i < _list.length; i++) {
                    var body = "<tr>" +
                            "<td style='display: none;'>" + _list[i].id + "</td>" +
                            "<td>" + _list[i].url + "</td>" +
                            "<td>" + _list[i].urlDesc + "</td>" +
                            "<td><a href='javascript:void(0)' onclick='delete_url(this)'><span class='glyphicon glyphicon-remove' style='color: red;'></span></a></td>" +
                            "</tr>";
                    _table.find("tbody").append(body);
                }
            });
        }
        function delete_url(str) {
            var id = $(str).parents("tr").find("td:eq(0)").html();
            var cof=confirm("是否要删除该统计Url?");
            if(cof){
                $.get("/pftstis/delete",{id:id},function(res){
                    if(res.result=="1"){
                        $(str).parents("tr").remove();
                    }else{
                        alert("删除失败");
                    }
                });
            }
        }

        function IsURL(str_url){
            var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
                    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                    + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                    + "|" // 允许IP和DOMAIN（域名）
                    + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
                    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
                    + "[a-z]{2,6})" // first level domain- .com or .museum
                    + "(:[0-9]{1,4})?" // 端口- :80
                    + "((/?)|" // a slash isn't required if there is no file name
                    + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
            var re=new RegExp(strRegex);
            //re.test()
            if (re.test(str_url)){
                return (true);
            }else{
                return (false);
            }
        }

        function Test() {
            $("div[class^='alert']").toggle();
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="../count/count_head.jsp"/>
    <jsp:include page="../count/count_nav.jsp"/>
    <div class="count_content clearfix">
        <div class="row">
            <div class="col-md-12">
                <div class="well well-sm">
                    <span style="font-weight: bold;">配置页面</span>&nbsp;
                    <a href="javascript:void(0)" onclick="Test();"><span class="glyphicon glyphicon-question-sign"></span></a>&nbsp;
                </div>
                <div class="alert alert-success" role="alert" style="display: none">
                    <p>本页面提供配置要统计的页面，页面目前最大支持4个</p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">设置统计URL地址</div>
                    <div class="panel-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                            <label for="url" class="col-sm-2 control-label">Url地址</label>
                            <div class="col-sm-10">
                                <input  class="form-control" id="url" placeholder="Url..">
                            </div>
                        </div>
                            <div class="form-group">
                                <label for="urlDesc" class="col-sm-2 control-label">Url描述</label>
                                <div class="col-sm-10">
                                    <input  class="form-control" id="urlDesc" placeholder="Url描述">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <input type="button" class="btn btn-default" value="添加"/>
                                </div>
                            </div>
                        </form>
                    </div>
                    <table class="table">
                        <thead>
                        <tr>
                            <td>Url地址</td>
                            <td>Url描述</td>
                            <td>删除</td>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-6">关键词，创意，转化事件设置</div>
        </div>
    </div>
</div>
</body>
</html>
