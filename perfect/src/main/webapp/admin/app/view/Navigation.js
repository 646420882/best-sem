/**
 * Created by XiaoWei on 2014/10/13.
 */


Ext.define("Perfect.view.Navigation", {
    extend: "Ext.panel.Panel",
    alias: "widget.nav",
    collapsible: true,
    title: '导航栏',
    split: true,
    width: 250,
    minWidth: 180,
    maxWidth: 300,
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
            icon:'icons/application_xp_terminal.png',
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
                },
                {
                    text: '百度帐号启用/禁用',
                    handler: function () {
                        var tabs = Ext.getCmp("tabs");
                        if (tabs.getComponent("tab5") == undefined) {
                            var _thisTabs = tabs.add({
                                id: "tab5",
                                xtype: "QY",
                                closable: true
                            });
                            tabs.setActiveTab(_thisTabs);
                        }else{
                            tabs.setActiveTab(tabs.getComponent("tab5"));
                        }
                    }
                }
            ]
        },
        {
            xtype: 'panel',
            title: '其他功能',
            icon:'icons/arrow_branch.png',
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
        },
        {
            xtype: "panel",
            title: "二期(人工片段库)",
            icon:'icons/flag_red.png',
            items: [
                {
                    text: '行业库',
                    handler:function(){
                        var tabs = Ext.getCmp("tabs");
                        if (tabs.getComponent("tab_hyk") == undefined) {
                            var _thisTabs = tabs.add({
                                id: "tab_hyk",
                                xtype: "HYK",
                                closable: true
                            });
                            tabs.setActiveTab(_thisTabs);
                        }else{
                            tabs.setActiveTab(tabs.getComponent("tab_hyk"));
                        }
                    }
                }
//                {
//                    text: '能拖拽的树'
//                },
//                {
//                    text: '树节点'
//                },
//                {
//                    text: '组合树'
//                }
            ]
        }
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
