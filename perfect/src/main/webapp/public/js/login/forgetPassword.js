/**
 * 忘记密码单击事件
 */
$("#forgetPassword").click(function () {
    var userName = $("#j_username").val();
    userName = userName.replace(/ /g,"");
    if(userName.length==0){
        alert("请输入要找回的账号");
        return;
    }

    var XMLHttpReq;
    function createXMLHttpRequest() {
        try {
            XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");//IE高版本创建XMLHTTP
        }
        catch(E) {
            try {
                XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");//IE低版本创建XMLHTTP
            }
            catch(E) {
                XMLHttpReq = new XMLHttpRequest();//兼容非IE浏览器，直接创建XMLHTTP对象
            }
        }

    }
    function sendAjaxRequest(url) {
        createXMLHttpRequest();                                //创建XMLHttpRequest对象
        XMLHttpReq.open("post", url, true);
        XMLHttpReq.onreadystatechange = processResponse; //指定响应函数
        XMLHttpReq.send(null);
    }
    //回调函数
    function processResponse() {
        if (XMLHttpReq.readyState == 4) {
            if (XMLHttpReq.status == 200) {
                var text = XMLHttpReq.responseText;
                if(text=='"userName Exists!"'){
                    alert("找回密码的邮件已发送至您的邮箱,请注意查收");
                }else{
                    window.location = "/forgetPassword/login?mes=该用户名不存在";
                }
            }
        }

    }

    sendAjaxRequest("/validate/validateUserNameIsExists?userName="+userName);

});