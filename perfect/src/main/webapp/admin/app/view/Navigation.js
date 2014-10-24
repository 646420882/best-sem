/**
 * Created by XiaoWei on 2014/10/13.
 */

Ext.onReady(function(){
    Ext.require("Perfect.view.model.GridOne");
    Ext.require("Perfect.view.model.JingJia");
    Ext.require("Perfect.view.model.CiKu");
    Ext.require("Perfect.view.model.Shenhe");
});

Ext.define("Perfect.view.Navigation", {
    extend: "Ext.panel.Panel",
    alias: "widget.nav",
    collapsible: true,
    title: '导航栏',
    split: true,
    width: 200,
    minWidth: 150,
    maxWidth: 250,
    layout: "accordion",
    defaults: {
        autoScroll: true,
        layout: {
            type: "vbox",
            pack: "start",
            align: "stretch"
        },
        defaults: {
            xtype: "button",
            height: 40,
            margins: '10 5 10 5'
        }
    },
    items: [
        {
            xtype: "panel",
            title: "系统功能",
            items: [
                {
                    text: '拉取数据',
                    handler: function () {
                        var tabs = Ext.getCmp("tabs");
                        if (tabs.getComponent("tab1") == undefined) {
                            var _thisTabs = tabs.add({
                                id: "tab1",
                                xtype: "suJu",
                                closable: true
                            });
                            tabs.setActiveTab(_thisTabs);
                        }else{
                            tabs.setActiveTab(tabs.getComponent("tab1"));
                        }
                    }
                },
                {
                    text: '智能竞价',
                    handler: function () {
                        var tabs = Ext.getCmp("tabs");
                        if (tabs.getComponent("tab2") == undefined) {
                        var _thisTabs = tabs.add({
                            id: "tab2",
                            xtype: "JJ",
                            closable: true
                        });
                        tabs.setActiveTab(_thisTabs);
                        }else{
                            tabs.setActiveTab(tabs.getComponent("tab2"));
                        }
                    }
                },
                {
                    text: '词库管理',
                    handler: function () {
                        var tabs = Ext.getCmp("tabs");
                        if (tabs.getComponent("tab3") == undefined) {
                            var _thisTabs = tabs.add({
                                id: "tab3",
                                xtype: "CK",
                                closable: true
                            });
                            tabs.setActiveTab(_thisTabs);
                        }else{
                            tabs.setActiveTab(tabs.getComponent("tab3"));
                        }
                    }
                },
                {
                    text: '账号审核',
                    handler: function () {
                        var tabs = Ext.getCmp("tabs");
                        if (tabs.getComponent("tab4") == undefined) {
                            var _thisTabs = tabs.add({
                                id: "tab4",
                                xtype: "ZH",
                                closable: true
                            });
                            tabs.setActiveTab(_thisTabs);
                        }else{
                            tabs.setActiveTab(tabs.getComponent("tab4"));
                        }
                    }
                }
            ]
        },
        {
            xtype: 'panel',
            title: '其他功能',
            items: [
                {
                    text: '数据结构',
                    handler:function(){
                        Ext.Msg.alert("提示","该功能还在开发中..");
                    }
                },
                {
                    text: '系统设置',
                    handler:function(){
                        Ext.Msg.alert("提示","该功能还在开发中..");
                    }
                }
            ]
        }
//        {
//            xtype: "panel",
//            title: "树示例",
//            items: [
//                {
//                    text: '树形表格'
//                },
//                {
//                    text: '能拖拽的树'
//                },
//                {
//                    text: '树节点'
//                },
//                {
//                    text: '组合树'
//                }
//            ]
//        },
//        {
//            xtype: "panel",
//            title: "图表",
//            items: [
//                {
//                    text: '小数图表'
//                },
//                {
//                    text: '3D图表'
//                },
//                {
//                    text: '折线图表'
//                },
//                {
//                    text: '立方图表'
//                }
//            ]
//        }
    ]

});
