/**
 * Created by XiaoWei on 2014/11/19.
 */
//var url = ["http://182.150.24.24:18080/home", "http://182.150.24.24:18080/login", "http://182.150.24.24:18080/bidding/index", "http://182.150.24.24:18080/keyword_group"];
var s = ["今日", "昨日"];
var _s = ["t", "ld"];
$(function(){
    $("a[href='toggle']").click(function () {
        if($(this).html()=="+"){
            $(this).html("-");
        }else{
            $(this).html("+");
        }
        var _this = $(this);
        $("div[id^='dir']").toggle();
        return false;
    });

    render();

});

function Test() {
    $("div[class^='alert']").toggle();
}
function getRamdom(str, count) {
    if (count == 0) {
        return parseInt(Math.random() * str);
    }
    return parseFloat(Math.random() * str).toFixed(count);
}

function getGrid(statusUrl, l) {
    $.get("/pftstis/getTodayConstants", {url: statusUrl}, function (res) {
        var json = $.parseJSON(res);
        var _showUrl=$(".panel-heading:eq("+l+")").find("a:eq(0)");
        var _table_01 = $("table:eq(" + l + ")");
        var _tbody = _table_01.find("tbody");
        for (var i = 0; i < s.length; i++) {
            var _val = "<tr><td>" + s[i] + "</td><td>" + json[_s[i]].totalCount + "</td><td>" + json[_s[i]].totalUv + "</td><td>" + json[_s[i]].totalIp + "</td><td>" + getRamdom(100, 2) + "%</td><td>00:02:" + getRamdom(100, 0) + "</td><td>" + getRamdom( json[_s[i]].totalCount, 0) + "</td></tr>";
            _tbody.append(_val);
        }
        var _thisVal = "<tr><td>预计今日</td><td>" + parseInt(json.lw.totalCount / 7) + renderIcon(json) + "</td><td>" + parseInt(json.lw.totalUv / 7) + renderIcon(json)+"</td><td>" + parseInt(json.lw.totalIp / 7) + renderIcon(json)+"</td><td>--</td><td>--</td><td>--</td></tr>";
        _tbody.append(_thisVal);
        _showUrl.attr("href", json.t.censusUrl).text(json.t.censusUrl);
    });
}
function render() {
    var  ipset = ((returnCitySN["cip"] == undefined) ? "" : returnCitySN["cip"]);
    var url=[];
    $.get("/pftstis/getCfgList",{ip:ipset},function(res){
        if(res!=undefined){
            var rows=res.rows;
            for(var i=0;i<rows.length;i++){
                var _showDesc=$(".panel-heading:eq("+i+")").find("span:eq(0)");
                url.push(rows[i].url);
                _showDesc.html(rows[i].urlDesc);
            }
            for (var i = 0; i < url.length; i++) {
                getGrid(url[i], i);
            }
        }
    });

}
function renderIcon(json) {
    var lwAve=parseInt(json.lw.totalCount/7);
    var ld=json.ld.totalCount;
    if(ld>=lwAve){
        return "<span class='glyphicon glyphicon-arrow-up'' style='color: red;'></span>";
    }else{
        return "<span class='glyphicon glyphicon-arrow-down'' style='color: green;'></span>";
    }
}

