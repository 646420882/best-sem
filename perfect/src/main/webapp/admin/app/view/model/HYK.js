/**
 * Created by XiaoWei on 2014/10/28.
 */
var Le = Ext.define("Le", {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'tr', type: 'string'}
    ]

});
Ext.create("Ext.data.Store", {
    storeId: 'storeTr',
    model: "Le",
    proxy: {
        type: 'ajax',
        url: '../person/getHyk',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }

});
Ext.define("Perfect.view.model.HYK", {
    extend: 'Ext.form.Panel',
    alias: 'widget.HYK',
    bodyPadding: 10,
    title: '行业库',
    items: [
        {
            xtype: "form",
            titel: '查询',
            items: [
                {
                    fieldLabel: '选择行业',
                    xtype: 'combobox',
                    query: 'remote',
                    name: 'trade',
                    displayField: 'tr',
                    valueField: 'tr',
                    emptyText: '行业..',
                    anchor:'100%',
                    editable: false,
                    store: Ext.StoreManager.lookup("storeTr")
                }
            ]
        },
        {
            xtype: "form",
            title: '提交',
            url: '../person/saveTr',
            border: true,
            bodyPadding: 10,
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: '行业名',
                    msgTarget: 'side',
                    anchor:'100%',
                    name: 'tr',
                    allowBlank: false,
                    blankText: '你准备连行业名都填么？你叶大爷的...'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '行业类别',
                    name: 'cg',
                    anchor:'100%',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '分组',
                    name: 'gr',
                    anchor:'100%',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '关键字',
                    name: 'kw',
                    anchor:'100%',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'url',
                    msgTarget: 'side',
                    anchor:'100%',
                    name: 'url',
                    allowBlank: false
                }
            ],
            buttons: [
                {
                    text: '提交',
                    handler: function () {
                        var _form = this.up("form").getForm();
                        if (_form.isValid()) {
                            _form.submit({
                                waitMsg: '正在提交中..',
                                success: function (form, action) {
                                    if (action.result.success == 1) {
                                        form.reset();
                                        Ext.Msg.alert("提示", "添加成功!");
                                    }
                                },
                                failure: function (form, action) {
                                    Ext.Msg.alert("提示!", "访问异常!");
                                }
                            });
                        }
                    }
                }
            ]
        }
    ]
});