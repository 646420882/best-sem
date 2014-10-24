/**
 * Created by XiaoWei on 2014/10/16.
 */
Ext.create("Ext.data.Store", {
    fields: ["text", "id"],
    storeId:'typeStore',
    data: [
        {'text': '电商', 'id': '0'},
        {'text': '房产', 'id': 1},
        {'text': '教育', 'id': 2},
        {'text': '金融', 'id': 3},
        {'text': '旅游', 'id': 4}
    ]
});
var Url = Ext.create('Url', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'category', type: 'string'},
        {name: 'count', type: 'int'}
    ]
});
Ext.create("Ext.data.Store", {
    storeId: "hTypeStore",
    model: 'Url',
    proxy: {
        type: 'ajax',
        url: '../getKRWords/getCategories',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
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
                    name: 'excelFile',
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
                                url: 'lexicon/upload',
                                waitMsg: '数据导入中...',
                                success: function (fp, o) {
                                    Ext.Msg.alert('成功', '你的文件 "' + o.result.data + '" 已经导入成功.');
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
            referenceHolder: true,
            viewModel:true,
            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: '选择行业',
                    id: "trade",
                    name: "category",
                    reference: 'one',
                    allowBlank: false,
                    afterLabelTextTpl: required,
                    msgTarget: 'side',
                    displayField: 'text',
                    valueField: 'text',
                    store:Ext.StoreManager.lookup("typeStore"),
                    listeners: {
                        change: function () {
                            var combox = Ext.getCmp("hType");
                            combox.setStore(Ext.StoreManager.lookup("hTypeStore").load({
                                params: {
                                    trade: this.getValue()
                                }
                            }));
                            combox.setDisabled(false);
                        }
                    }
                },
                {
                    xtype: 'combobox',
                    fieldLabel: '类别',
                    id: "hType",
                    name: 'trade',
                    allowBlank: false,
                    afterLabelTextTpl: required,
                    msgTarget: 'side',
                    displayField: 'category',
                    valueField: 'category',
                    disabled:true,
                    store:null
                }
            ],
            buttons: [
                {
                    text: '刪除',
                    handler:function(){
                        var form = this.up('form').getForm();
                        if (form.isValid()) {
                            Ext.Msg.show({
                                title:' ',
                                message:'你确定要执行该删除操作?一旦删除将不能还原，请谨慎操作!',
                                icon:Ext.Msg.WARNING,
                                buttons:Ext.Msg.YESNO,
                                fn:function(chioce){
                                    if(chioce=="yes"){
                                        form.submit({
                                            url: "lexicon/delete",
                                            waitMsg: "删除中，请等待...",
                                            success: function (form, action) {
                                                Ext.Msg.alert("提示", "删除成功!");
                                            },
                                            failure: function (form, action) {

                                            }
                                        });
                                    }
                                }
                            });

                        }
                    }
                }
            ],
            tools: [
                {
                    type: 'refresh',
                    handler: function () {
                        var box1 = Ext.getCmp("trade");
                        var box2 = Ext.getCmp("hType");
                        if (box1.isValid()) {
                            box2.setStore(Ext.StoreManager.lookup("hTypeStore").load({
                                params: {
                                    trade: box1.getValue()
                                }
                            }));
                        }
                    }
                }
            ]
        }
    ]

});
