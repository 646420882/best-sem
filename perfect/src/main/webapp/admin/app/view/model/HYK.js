/**
 * Created by XiaoWei on 2014/10/28.
 */
var Le = Ext.define("Le", {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'trade', type: 'string'}
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
var toolbar = Ext.widget('toolbar', {
    bodyPadding: 10,
    items: [
        {
            fieldLabel: '选择行业',
            xtype: 'combobox',
            query: 'remote',
            name: 'trade',
            id: 'tradeComboBox',
            displayField: 'trade',
            valueField: 'trade',
            emptyText: '行业..',
            allowBlank: false,
            afterLabelTextTpl: required,
            msgTarget: 'side',
            editable: false,
            store: Ext.StoreManager.lookup("storeTr"),
            listeners: {
                change: function () {
                    var combox = Ext.getCmp("cateCombobox");
                    combox.setStore(Ext.StoreManager.lookup("cateStore").load({
                        params: {
                            trade: this.getValue()
                        }
                    }));
                    combox.setDisabled(false);
                }
            }
        },{
            xtype: 'combobox',
            query: 'remote',
            fieldLabel: '类别',
            id: "cateCombobox",
            name: 'trade',
            emptyText: '类别..',
            allowBlank: false,
            afterLabelTextTpl: required,
            msgTarget: 'side',
            displayField: 'category',
            valueField: 'category',
            disabled:true,
            editable: false,
            store:null
        },{
            text:'查询',
            handler:function(){
                var _trade=Ext.getCmp("tradeComboBox");
                var _category=Ext.getCmp("cateCombobox");
                if(_trade.isValid()&&_category.isValid()){
                    hykStore.load({
                        params:{
                            trade:_trade.getValue(),
                            category:_category.getValue()
                        }
                    });
                }

            }
        }
    ]
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
    model:'hykModel',
    pageSize: 14,
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
hykStore.load({
    params:{
        trade:Ext.getCmp("tradeComboBox").getValue(),
        category:Ext.getCmp("cateCombobox").getValue()
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
            title: '查询',
            bodyPadding:10,
            icon: 'icons/zoom.png',
            border:true,
            items: [
                {
                    xtype:'grid',
                    title:'行业库列表',
                    tbar: toolbar,
                    loadMask:true,
                    store: hykStore,
                    columns:[
                        {text: '行业名', dataIndex: 'trade'},
                        {text: '类别', dataIndex: 'category', flex: 1},
                        {text: '分组', dataIndex: 'group', flex: 1},
                        {text: '关键字', dataIndex: 'keyword', flex: 1},
                        {text: 'Url', dataIndex: 'url', flex: 2}
                    ],
                    bbar: {
                        xtype: 'pagingtoolbar',
                        store: hykStore,
                        plugins: new Ext.ux.ProgressBarPager(),
                        listeners:function(){
                            click:{
                                Ext.Msg.alert("提示","你要爪子嘛？");
                            }
                        }
                    }
                }
            ]
        },
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
                    text: '提交',
                    handler: function () {
                        var _form = this.up("form").getForm();
                        if (_form.isValid()) {
                            _form.submit({
                                waitMsg: '正在提交中..',
                                success: function (form, action) {
                                    if (action.result.success == 1) {
                                        form.reset();
                                        Ext.StoreManager.lookup("storeTr").load();
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
        }
    ]
});
