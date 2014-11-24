/**
 * Created by XiaoWei on 2014/10/13.
 */
Ext.onReady(function(){
    Ext.require("Perfect.view.Navigation");
    Ext.require("Perfect.view.model.GridOne");
    Ext.require("Perfect.view.model.JingJia");
    Ext.require("Perfect.view.model.CiKu");
    Ext.require("Perfect.view.alert.B1");
    Ext.require("Perfect.view.model.SH");
    Ext.require("Perfect.view.model.HYK");
    Ext.require("Perfect.view.model.QY");
    Ext.require("Perfect.view.tbar.HykToolbar");
});
Ext.Date.patterns = {
    ISO8601Long:"Y-m-d H:i:s",
    ISO8601Short:"Y-m-d",
    ShortDate: "n/j/Y",
    LongDate: "l, F d, Y",
    FullDateTime: "l, F d, Y g:i:s A",
    MonthDay: "F d",
    ShortTime: "g:i A",
    LongTime: "g:i:s A",
    SortableDateTime: "Y-m-d\\TH:i:s",
    UniversalSortableDateTime: "Y-m-d H:i:sO",
    YearMonth: "F, Y"
};
var finalTrade="";
var finalCategory="";
var dt = new Date();
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Perfect.view.Viewport", {
    extend: "Ext.container.Viewport",
    layout:'border',
    items: [{
        region: 'north',
        border: true,
        margin: '0 0 5 0',
        xtype:'toolbar',
        items:['<h1 class="x-panel-header">Perfect后台管理系统.</h1>','->',
            {
                text:'退出',
                handler:function(){
                    Ext.Msg.show({
                        title:"提示!",
                        message:'你确定要退出后台系统么？',
                        buttons:Ext.Msg.YESNOCANCEL,
                        icon:Ext.Msg.QUESTION,
                        fn:function(btn){
                            if(btn=="yes"){
                                Ext.Ajax.request({
                                   url:'logout',
                                    success:function(response){
                                        window.location='login'
                                    }
                                });
                            }
                        }
                    });
                }
            }
        ]
    }, {
        region: 'west',
        xtype:"nav"
    }, {
        xtype:"toolbar",
        region: 'south',
        split: true,
        items:["当前日期："+Ext.Date.format(dt, Ext.Date.patterns.ISO8601Short)+"",'->','&copy;成都松石科技有限公司'],
        height:50,
        maxHeight:50,
        minHeight:50
    }, {
        region: 'east',
        title: '右边面板',
        collapsible: true,
        collapsed: true,
        split: true,
        width: 250,
        maxWidth:350,
        minWidth:180
    }, {
        region: 'center',
        id:'tabs',
        xtype: 'tabpanel',
        activeTab: 0,     // 默认选中第几个选项卡
        items:[
            {
                xtype:"suJu",
                id:'tab1',
                closable:true
            }
        ]
    }]

});
