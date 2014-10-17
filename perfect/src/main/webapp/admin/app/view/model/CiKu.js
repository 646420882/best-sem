/**
 * Created by XiaoWei on 2014/10/16.
 */
var comboboxSotre = Ext.create("Ext.data.Store", {
    fields: ["text", "id"],
    data: [
        {'text': '电商', 'id': 0},
        {'text': '房产', 'id': 1},
        {'text': '教育', 'id': 2},
        {'text': '金融', 'id': 3},
        {'text': '旅游', 'id': 4}
    ]
});
Ext.define("Perfect.view.model.CiKu", {
    extend: 'Ext.form.Panel',
    alias: 'widget.CK',
    bodyPadding: 35,
    title: '词库管理',
    items: [
        {
            xtype: 'form',
            border:true,
            title: '词库添加',
            bodyPadding: 10,
            collapsible: true,
            items: [
                {
                    xtype: 'filefield',
                    name: 'document',
                    fieldLabel: '请选择文件',
                    msgTarget: 'side',
                    allowBlank: false,
                    buttonText: '选择文件..',
                    anchor:'100%'
                }
            ],
            buttons: [
                {
                    text: '导入',
                    handler: function () {
                        var form = this.up('form').getForm();
                        if (form.isValid()) {
                            form.submit({
                                url: 'upload.action',
                                waitMsg: '数据导入中...',
                                success: function (fp, o) {
                                    Ext.Msg.alert('成功', '你的文件 "' + o.result.file + '" 已经导入成功.');
                                },
                                failure: function (form, action) {
                                    Ext.Msg.alert('失败', '未知错误');
                                }
                            });
                        }
                    }
                }
            ]
        },
        {
            xtype: 'form',
            margin:"20 0 0 0",
            border:true,
            title: '词库内容删除',
            bodyPadding: 10,
            collapsible: true,
            defaults:{
                anchor:'100%'
            },
            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: '选择行业',
                    allowBlank: false,
                    afterLabelTextTpl: required,
                    store: comboboxSotre,
                    msgTarget: 'side',
                    displayField: 'text',
                    valueField: 'id'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: '选择行业2',
                    allowBlank: false,
                    afterLabelTextTpl: required,
                    msgTarget: 'side',
                    displayField: 'text',
                    valueField: 'id'

                }
            ],
            buttons: [
                {
                    text: '刪除',
                    handler:function(){
                        var form = this.up('form').getForm();
                        if (form.isValid()) {
                            Ext.Msg.alert('提示!','可以删除了！');
                        }
                    }
                }
            ]
        }
    ]

});
