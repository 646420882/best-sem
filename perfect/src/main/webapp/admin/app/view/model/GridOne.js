/**
 * Created by XiaoWei on 2014/10/15.
 */
var comboboxSotre = Ext.create("Ext.data.Store", {
    fields: ["text", "id"],
    data: [
        {'text': '拉取全部报告', 'id': 0},
        {'text': '拉取账户报告', 'id': 1},
        {'text': '拉取计划报告', 'id': 2},
        {'text': '拉取单元报告', 'id': 3},
        {'text': '拉取创意报告', 'id': 4},
        {'text': '拉取关键词报告', 'id': 5},
        {'text': '拉取地域报告', 'id': 6}
    ]
});

Ext.define("Perfect.view.model.GridOne", {
    extend: "Ext.panel.Panel",
    alias: "widget.suJu",
    title: "拉取数据",
    bodyPadding: 10,
    items: [
        {
            xtype: 'toolbar',
            items: [
                {
                    text: '注意：',
                    disabled: true
                },
                '<span style="color: red;">如果没有选取时间则默认拉取昨天的数据,拉取时间较长，请勿关闭页面。</span>'
            ]
        },
        {
            xtype: 'form',
            url: 'reportPull/getPullDatas',
            layout: 'anchor',
            bodyPadding: 5,
            border: true,
            fieldDefaults: {
                labelAlign: 'left',
                msgTarget: 'side'
            },
            defaults: {
                anchor: '100%'
            },
            items: [
                {
                    xtype: 'container',
                    layout: 'hbox',

                    items: [
                        {
                            xtype: 'container',
                            flex: 1,
                            border: false,
                            layout: 'anchor',
                            defaultType: 'datefield',
                            items: [
                                {
                                    fieldLabel: '开始时间',
                                    name: 'startDate',
                                    anchor: '100%',
                                    format:'Y-m-d'
                                },
                                {
                                    fieldLabel: '结束时间',
                                    name: 'endDate',
                                    anchor: '100%',
                                    format:'Y-m-d'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    flex: 1,
                    border: false,
                    layout: 'anchor',
                    defaultType: 'combobox',
                    items: [
                        {
                            fieldLabel: '选择拉取类型',
                            store: comboboxSotre,
                            queryMode: 'local',
                            name:'pullObj',
                            displayField: 'text',
                            valueField: 'id',
                            emptyText: '拉取类型',
                            allowBlank: false,
                            afterLabelTextTpl: required,
                            anchor: '100%'
                        }
                    ]
                },
              /*  {
                    xtype:'',
                    fieldLabel: ' <sapn style="color: #ff0000">注：如果不填写帐号拉取的是全账户的报告数据，填写后则是拉取填写帐号的报告数据</sapn>'
                },*/
                {
                    xtype:'textfield',
                    fieldLabel: '拉取帐号',
                    name:'userName'

                }

            ],
            buttons: [
                {
                    text: '重置',
                    handler: function () {
                        this.up("form").getForm().reset();
                    }
                },
                {
                    text: '确定',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        var form = this.up("form").getForm();
                        if (form.isValid()) {
                            form.submit({
                                waitTitle: "提示",
                                waitMsg: '数据拉取中...',
                                timeout:'18000',
                                success: function (form, action) {
                                    if (action.result.success == 1) {
                                        Ext.Msg.alert("提示", "数据拉取成功!");
                                    }else if (action.result.success == 0) {
                                        Ext.Msg.alert("提示", "拉取失败");
                                    }else {
                                        Ext.Msg.alert("提示", "异常");
                                    }
                                },
                                failure: function (form, action) {
                                    Ext.Msg.alert("提示", "访问异常！");
                                    form.reset();
                                }
                            });
                        }
                    }
                }
            ],
            tabbar:{
                items:[
                    '<sapn style="color: #ff0000">注：如果不填写帐号拉取的是全账户的报告数据，填写后则是拉取填写帐号的报告数据</sapn>'
                ]
            }

        }
    ]
});