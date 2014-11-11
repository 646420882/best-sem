var sysArrayp = new Array(),iptest,regions,intId= 0,intEnd = 0,interval;
;(function () {
    loadScript("http://pv.sohu.com/cityjson?ie=utf-8",function () {
        if(typeof(returnCitySN) == "undefined"){
            returnCitySN = new Array();
        }
        regions = ((returnCitySN["cname"] == undefined)?"":returnCitySN["cname"]);
        iptest = ((returnCitySN["cip"] == undefined)?"":returnCitySN["cip"]);
        intId=1;
    });
    interval = setInterval('suumitScript()',1000);
}());
//提交操作
function suumitScript(){
    var script=document.getElementById('ScriptId');
    if (script)
    {
        document.getElementsByTagName("head")[0].removeChild(script);
    }
    var url= new Array("/pftstis/statistics","/pftstis/getCookie");

    if(intId ==1){
        for(var i=0;i<url.length;i++){
            alert(url[i]);
            script=document.createElement("script");
            script.type="text/javascript";
            script.id="ScriptId";
            script.charset="utf-8";
            script.src=url[i]+"?osAnBrowser="+getOSAndBrowser();
            document.getElementsByTagName("head")[0].appendChild(script);
        }
        clearInterval(interval)
    }else{
        intEnd++;
        if(intEnd==1){
            for(var i=0;i<url.length;i++){
                script=document.createElement("script");
                script.type="text/javascript";
                script.id="ScriptId";
                script.charset="utf-8";
                script.src=url[i]+"?osAnBrowser="+getOSAndBrowser();
                document.getElementsByTagName("head")[0].appendChild(script);
            }
            clearInterval(interval)
        }
    }
}
//获取cookie值
function getCookieValue(name) {
    var name = escape(name);
    var allcookies = document.cookie;
    name += "=";
    var pos = allcookies.indexOf(name);
    if (pos != -1) {
        var start = pos + name.length;
        var end = allcookies.indexOf(";", start);
        if (end == -1) end = allcookies.length;
        var value = allcookies.substring(start, end);
        return unescape(value);
    }
    else return -1;
}

function getOSAndBrowser(){
    // 0 UUID
    // 1 电脑系统
    // 2 浏览器版本
    // 3 屏幕分辨率
    // 4 是否支持cookie
    // 5 是否支持java
    // 6 屏幕颜色
    // 7 flash版本
    // 8 客户端访问目标地址时间
    // 9 客户端网页的访问来源
    // 10 用户IP地址
    // 11 用户所在地区
    // 12 移动或PC访问（1 移动端访问网页  0 PC端访问网页）
    var os = navigator.platform;
    var userAgent = navigator.userAgent;
    var tempArray  = "";

    var cookie_pos = document.cookie.indexOf("PFT_USER_UUID");
    if (cookie_pos == -1) {
        document.cookie = "PFT_USER_UUID=" + createUUID();
    }
    sysArrayp[0] = getCookieValue("PFT_USER_UUID");

    if(os.indexOf("Win") > -1){
        if(userAgent.indexOf("Windows NT 5.0") > -1){
            sysArrayp[1] = "Windows 2000";
        }else if(userAgent.indexOf("Windows NT 5.1") > -1){
            sysArrayp[1] = "Windows XP";
        }else if(userAgent.indexOf("Windows NT 5.2") > -1){
            sysArrayp[1] = "Windows 2003";
        }else if(userAgent.indexOf("Windows NT 6.0") > -1){
            sysArrayp[1] = "Windows Vista";
        }else if(userAgent.indexOf("Windows NT 6.1") > -1 || userAgent.indexOf("Windows 7") > -1){
            sysArrayp[1] = "Windows 7";
        }else if(userAgent.indexOf("Windows 8") > -1){
            sysArrayp[1] = "Windows 8";
        }else{
            sysArrayp[1] = "Other";
        }
    }else if(os.indexOf("Mac") > -1){
        sysArrayp[1] = "Mac";
    }else if(os.indexOf("X11") > -1){
        sysArrayp[1] = "Unix";
    }else if(os.indexOf("Linux") > -1){
        sysArrayp[1] = "Linux";
    }else{
        sysArrayp[1] = "Other";
    }
    if(/[Ff]irefox(\/\d+\.\d+)/.test(userAgent)){
        tempArray = /([Ff]irefox)\/(\d+\.\d+)/.exec(userAgent);
        sysArrayp[2] = tempArray[1] + tempArray[2];
    }else if(/MSIE \d+\.\d+/.test(userAgent)){
        tempArray = /MS(IE) (\d+\.\d+)/.exec(userAgent);
        sysArrayp[2] = tempArray[1] + tempArray[2];
    }else if(/[Cc]hrome\/\d+/.test(userAgent)){
        tempArray = /([Cc]hrome)\/(\d+)/.exec(userAgent);
        sysArrayp[2] = tempArray[1] + tempArray[2];
    }else if(/[Vv]ersion\/\d+\.\d+\.\d+(\.\d)* *[Ss]afari/.test(userAgent)){
        tempArray = /[Vv]ersion\/(\d+\.\d+\.\d+)(\.\d)* *([Ss]afari)/.exec(userAgent);
        sysArrayp[2] =  tempArray[3] + tempArray[1];
    }else if(/[Oo]pera.+[Vv]ersion\/\d+\.\d+/.test(userAgent)){
        tempArray = /([Oo]pera).+[Vv]ersion\/(\d+)\.\d+/.exec(userAgent);
        sysArrayp[2] =  tempArray[1] + tempArray[2];
    }else{
        sysArrayp[2] = "unknown";
    }
    var v_flash;
    for (var i=0; i < navigator.plugins.length; i++) {
        if (navigator.plugins[i].name.toLowerCase().indexOf("shockwave flash") >= 0) {
            v_flash = navigator.plugins[i].description.substring(navigator.plugins[i].description.toLowerCase().lastIndexOf("flash ") + 6, navigator.plugins[i].description.length);
            v_flash = v_flash.substring(0,v_flash.indexOf(" "));
        }
    }
    sysArrayp[3] = window.screen.width+"x"+window.screen.height;
    sysArrayp[4] = navigator.cookieEnabled;
    sysArrayp[5] = navigator.javaEnabled();
    sysArrayp[6] = window.screen.colorDepth+"-bit";
    sysArrayp[7] = ((v_flash == undefined || v_flash == "")?"":v_flash);
    sysArrayp[8] = new Date();
    sysArrayp[9] = document.referrer;
    sysArrayp[10] = iptest;
    sysArrayp[11] = regions;
    sysArrayp[12] = getPcOrMobile();
    return sysArrayp;
};
function getPcOrMobile(){
    if(/AppleWebKit.*Mobile/i.test(navigator.userAgent)
        || /Android/i.test(navigator.userAgent)
        || /BlackBerry/i.test(navigator.userAgent)
        || /IEMobile/i.test(navigator.userAgent)
        || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))){
        if(/iPad/i.test(navigator.userAgent)){
            return 1;
        }else{
            return 1;
        }
    }else{
        return 0;
    }
}
//加载 读取IP script
function loadScript(url, callback) {
    var cnameOrIp;
    cnameOrIp=document.createElement("script");
    cnameOrIp.type="text/javascript";
    cnameOrIp.charset="utf-8";
    // IE
    if (cnameOrIp.readyState) {
        cnameOrIp.onreadystatechange = function () {
            if (cnameOrIp.readyState == "loaded" || cnameOrIp.readyState == "complete") {
                cnameOrIp.onreadystatechange = null;
                callback();
            }
        };
    } else { // others
        cnameOrIp.onload = function () {
            callback();
        }
    }
    cnameOrIp.src=url;
    document.getElementsByTagName("head")[0].appendChild(cnameOrIp);
}
//UUID生成
function toString() {
    return this.id;
};
function createUUID() {
    var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
    var dc = new Date();
    var t = dc.getTime() - dg.getTime();
    var tl = getIntegerBits(t, 0, 31);
    var tm = getIntegerBits(t, 32, 47);
    var thv = getIntegerBits(t, 48, 59) + '1';
    var csar = getIntegerBits(rand(4095), 0, 7);
    var csl = getIntegerBits(rand(4095), 0, 7);
    var n = getIntegerBits(rand(8191), 0, 7) +
        getIntegerBits(rand(8191), 8, 15) +
        getIntegerBits(rand(8191), 0, 7) +
        getIntegerBits(rand(8191), 8, 15) +
        getIntegerBits(rand(8191), 0, 15);
    alert(tl + tm + thv + csar + csl + n);
    return tl + tm + thv + csar + csl + n;
};
function getIntegerBits(val, start, end) {
    var base16 = returnBase(val, 16);
    var quadArray = new Array();
    var quadString = '';
    var i = 0;
    for (i = 0; i < base16.length; i++) {
        quadArray.push(base16.substring(i, i + 1));
    }
    for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++) {
        if (!quadArray[i] || quadArray[i] == '') quadString += '0';
        else quadString += quadArray[i];
    }
    return quadString;
};
function returnBase(number, base) {
    return (number).toString(base).toUpperCase();
};
function rand(max) {
    return Math.floor(Math.random() * (max + 1));
};
