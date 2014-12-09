/**
 * Created by XiaoWei on 2014/9/28.
 */
Ext.define('Demo1.controller.MyController', {
    extend: 'Ext.app.Controller',

    init: function (application) {
        this.control({
            '[id=btnSavePwd]': {
                click: this.onOK
            },
            '[id=pinBtn]':{
                click:this.onPin
            }
        });
    },

    //保存
    onOK: function (obj) {
        var form = obj.up('form').getForm();
        if (form.isValid()) {
            Ext.Msg.alert('信息提示', '已保存');
        }
    },
    onPin:function(obj){
        Ext.Msg.alert("信息提示","已经点击到我了",function(btn,text){
            if(btn=="ok"){
                Ext.Ajax.request({
                    url: 'page.php',
                    params: {
                        id: 1
                    },
                    success: function(response){
                        var text = response.responseText;
                        // process server response here
                    }
                });
            }
        });
    }
});