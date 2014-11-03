/**
 * Created by XiaoWei on 2014/10/28.
 */
var Le = Ext.define("Le", {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'trade', type: 'string'}
    ]
});
var storeTr = Ext.create("Ext.data.Store", {
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
var cateModel = Ext.define('cateModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'category', type: 'string'},
        {name: 'count', type: 'int'}
    ]
});
Ext.create("Ext.data.Store", {
    storeId: "cateStore",
    model: 'cateModel',
    proxy: {
        type: 'ajax',
        url: '../getKRWords/getCategories',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
});

var urlModel = Ext.define('urlModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'category', type: 'string'},
        {name: 'count', type: 'int'}
    ]
});
var hTypeStore = Ext.create("Ext.data.Store", {
    model: 'urlModel',
    proxy: {
        type: 'ajax',
        url: '../getKRWords/getCategories',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
});
var hykModel=Ext.define("hykModel",{
    extend:'Ext.data.Model',
    fields:[
        {name:'id',type:"string"},
        {name:'tr',type:'string'},
        {name:'cg',type:'string'},
        {name:'gr',type:'string'},
        {name:'kw',type:'string'},
        {name:'url',type:'string'}
    ]
});
var hykStore = Ext.create("Ext.data.Store", {
    pageSize: 13,
    model: 'hykModel',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: '../person/findPager',
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totalCount'
        }
    }


});
Ext.define("Perfect.view.model.HYK", {
    extend: 'Ext.form.Panel',
    alias: 'widget.HYK',
    bodyPadding: 10,
    title: '行业库',
    layout: "accordion",
    defaults: {
        autoScroll: true,
        layout: {
            type: "vbox",
            pack: "start",
            align: "stretch"
        }
    },
    items: [
        {
            xtype: "form",
            icon: 'icons/add.png',
            title: '添加行业库',
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
                    blankText: '你准备连行业名都不填么？你大爷的...'
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
                    text: '重置',
                    handler: function () {
                        this.up("form").getForm().reset();
                    }
                },
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
                                        storeTr.load();
                                        Ext.Msg.alert("提示", "添加成功!");
                                    }else if(action.result.success==0){
                                        Ext.Msg.alert("提示", "已经存在该\"行业名\"和\"关键字\"!");
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
        },
        {
            xtype: 'form',
            border: true,
            title: '导入词库',
            icon: 'icons/drive_add.png',
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
                    anchor: '100%'
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
            xtype: 'grid',
            title: '行业库列表',
            icon: 'icons/zoom.png',
            tbar: {
                xtype: 'hyktbar'
            },
            loadMask: {
                msg: '加载中...'
            },
            store: hykStore,
            columns: [
                {text: '行业名', dataIndex: 'trade'},
                {text: '类别', dataIndex: 'category', flex: 1},
                {text: '分组', dataIndex: 'group', flex: 1},
                {text: '关键字', dataIndex: 'keyword', flex: 1},
                {text: 'Url', dataIndex: 'url', flex: 2},
                {
                    xtype: 'actioncolumn',
                    text: '删除',
                    sortable: false,
                    menuDisabled: true,
                    items: [
                        {
                            icon: 'icons/cancel.png',
                            tooltip: '删除',
                            scope: this,
                            handler: onRemoveClick
                        }
                    ]
                }
            ],
            bbar: {
                xtype: 'pagingtoolbar',
                store: hykStore,
                displayInfo: true,
                plugins: new Ext.ux.ProgressBarPager()
            }
        },
        {
            xtype: 'form',
            border: true,
            title: '词库批量删除',
            icon: 'icons/delete.png',
            bodyPadding: 10,
            collapsible: true,
            defaults: {
                anchor: '100%'
            },
            referenceHolder: true,
            viewModel: true,
            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: '选择行业',
                    id: "trade",
                    name: "trade",
                    allowBlank: false,
                    afterLabelTextTpl: required,
                    msgTarget: 'side',
                    displayField: 'trade',
                    valueField: 'trade',
                    editable: false,
                    store: storeTr,
                    listeners: {
                        change: function () {
                            var combox = Ext.getCmp("hType");
                            combox.setStore(hTypeStore.load({
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
                    name: 'category',
                    allowBlank: false,
                    afterLabelTextTpl: required,
                    msgTarget: 'side',
                    displayField: 'category',
                    valueField: 'category',
                    disabled: true,
                    editable: false,
                    store: hTypeStore
                }
            ],
            buttons: [
                {
                    text: '刪除',
                    handler: function () {
                        var form = this.up('form').getForm();
                        if (form.isValid()) {
                            Ext.Msg.show({
                                title: ' ',
                                message: '你确定要执行该删除操作?一旦删除将不能还原，请谨慎操作!',
                                icon: Ext.Msg.WARNING,
                                buttons: Ext.Msg.YESNO,
                                fn: function (chioce) {
                                    if (chioce == "yes") {
                                        form.submit({
                                            url: "lexicon/delete",
                                            waitMsg: "删除中，请等待...",
                                            success: function (form, action) {
                                                var _type = Ext.getCmp("hType").setValue(null);
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
function onRemoveClick(grid, rowIndex) {
    var _column = hykStore.getAt(rowIndex);
    var trade = _column.get("trade");
    var category = _column.get("category");
    var group = _column.get("group");
    var keyword = _column.get("keyword");
    var url = _column.get("url");
    Ext.Msg.show({
        title: '提示',
        message: '你确定要删除该条数据吗？',
        buttons: Ext.Msg.YESNOCANCEL,
        icon: Ext.Msg.WARNING,
        fn: function (btn) {
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url: '../person/deleteByParams',
                    params:{
                        trade:trade,
                        keyword:keyword
                    },
                    success: function (result) {
                        var json = JSON.parse(result.responseText);
                        if (json.success == "1") {
                            hykStore.removeAt(rowIndex);
                        }
                    },
                    failure: function (result) {
                    }
                });

            }
        }
    });

}